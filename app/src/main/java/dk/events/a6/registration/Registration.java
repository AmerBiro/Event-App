package dk.events.a6.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;

import dk.events.a6.R;
import dk.events.a6.databinding.RegistrationRegisterationBinding;
import dk.events.a6.logic.FieldChecker;
import user.UserAuth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends Fragment implements View.OnClickListener {
    private RegistrationRegisterationBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private UserAuth userAuth;
    private FieldChecker checker;

    private EditText[] fields;
    private String[] errorMessage;

    private NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationRegisterationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userAuth = new UserAuth(getActivity(), view, controller);
        checker = new FieldChecker(getActivity());
        fields = new EditText[2];
        errorMessage = new String[2];
        fields[0] = binding.username;
        fields[1] = binding.password;
        errorMessage[0] = "Invalid Email";
        errorMessage[1] = "Invalid Password";
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.singIn.setOnClickListener(this);
        binding.guest.setOnClickListener(this);
        binding.signUp.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sing_in:
                signIn();
                break;
            case R.id.guest:

                break;
            case R.id.forgot_password:
                forgotPassword();
                break;
            case R.id.sign_up:
                controller.navigate(R.id.action_registeration_to_signUp);
                break;
            default:
        }
    }

    private void forgotPassword() {
        userAuth.resetPassword();
    }

    private void signIn() {
        if (!checker.isEmpty(fields, errorMessage))
        userAuth.signIn(binding.progressBar, fields[0], fields[1]);
    }

}