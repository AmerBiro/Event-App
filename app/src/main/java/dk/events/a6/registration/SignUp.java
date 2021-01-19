package dk.events.a6.registration;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;


import dk.events.a6.R;
import dk.events.a6.databinding.RegistrationSignUpBinding;
import dk.events.a6.logic.FieldChecker;
import dk.events.a6.user.UserAuth;
import dk.events.a6.user.UserDatebase;

import androidx.annotation.Nullable;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class SignUp extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FieldChecker checker;
    private UserAuth userAuth;
    private EditText[] fields;
    private String[] errorMessage;
    private UserDatebase userDatebase;
    private @NonNull
    RegistrationSignUpBinding
            binding;
    private NavController controller;
    private String userId;
    private int years, months, days;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        checker = new FieldChecker(getActivity());
        userAuth = new UserAuth(getActivity(), view, controller);
        userDatebase = new UserDatebase(controller, view, getActivity());
        fields = new EditText[5];
        errorMessage = new String[5];
        fields[0] = binding.firstName;
        fields[1] = binding.lastName;
        fields[2] = binding.dateOfBirth;
        fields[3] = binding.email;
        fields[4] = binding.password;
        errorMessage[0] = "Invalid username";
        errorMessage[1] = "Invalid last name";
        errorMessage[2] = "Invalid date of birth";
        errorMessage[3] = "Invalid email";
        errorMessage[4] = "Invalid password";
        binding.dateOfBirth.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        days = calendar.get(Calendar.DATE);
        binding.dateSelector.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view1, year, month, dayOfMonth) -> {
                months = month+1;
                days = dayOfMonth;
                years = year;
                String date = days + "/" + months + "/" + years;
                binding.dateOfBirth.setText(date);
            }, years, months,days);
            datePickerDialog.show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.dateOfBirth.setOnClickListener(this);
        binding.arrowNext.setOnClickListener(this);
        binding.arrowBackSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_next:
                createAccount();
                break;
            case R.id.arrow_back_sign_up:
                controller.navigate(R.id.action_signUp_to_registeration);
                controller.navigateUp();
                controller.popBackStack();
            default:
        }

    }

    private void createAccount() {
        if (!checker.isEmpty(fields, errorMessage)){
            if (!checker.genderCheck(binding.radioGroup)) {
                binding.progressBar.setVisibility(View.VISIBLE);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(
                        fields[3].getText().toString(),
                        fields[4].getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userId = authResult.getUser().getUid();
                        Log.d(TAG, "onSuccess: " + "A new user has been created with userId: " + userId);

                        userDatebase.uploadUserInfoToFirebase(userId, R.id.action_signUp_to_backgroundInfo, binding.progressBar,
                                fields[0], fields[1], fields[2], fields[3], checker.getGender());
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), 1).show();
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        return;
                    }
                });
            }
        }
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        String birthDate = DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(calendar.getTime());
        binding.dateOfBirth.setText(birthDate);
    }
}