package dk.events.a6.signInView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.eventsapp.functions.DatePicker;
import dk.events.a6.R;
import dk.events.a6.databinding.ActivitySignUpBinding;
import dk.events.a6.signInView.functions.FieldChecker;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Sign_Up extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String TAG = "TAG";
    private ActivitySignUpBinding binding;
    FieldChecker checker;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private FirebaseFirestore fStore;
    private StorageReference storageReference;

     private String Users;
     private EditText First_Name;
     private EditText Last_Name;
     private EditText Birthdate;
     private EditText Email;
     private EditText Password;
     private String Gender;

     private String toStringFirst_Name;
     private String toStringLast_Name;
     private String toStringBirthdate;
     private String toStringEmail;
     private String toStringPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checker = new FieldChecker();
        Users = "Users";
        First_Name = binding.FirstName;
        Last_Name = binding.LastName;
        Birthdate = binding.BirthdatePicker;
        Email = binding.profileEmail;
        Password = binding.Password;
        Gender = "";

        toStringFirst_Name = binding.FirstName.getText().toString();
        toStringLast_Name = binding.LastName.getText().toString();
        toStringBirthdate = binding.BirthdatePicker.getText().toString();
        toStringEmail = binding.profileEmail.getText().toString();
        toStringPassword = binding.Password.getText().toString();



        binding.BirthdatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePicker();
                dialogFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(Sign_Up.this, ProfileInfo.class );
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        binding.NextArrowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        checker.allFieldsEmpty(Sign_Up.this, First_Name, Last_Name, Birthdate, Email, Password) ||
                        checker.someFieldsEmpty(Sign_Up.this, First_Name, Last_Name, Birthdate, Email, Password) ||
                        checker.genderCheck(Sign_Up.this, binding.radioGroup, Gender))
                    return;
                else {
                    mAuth.createUserWithEmailAndPassword(binding.profileEmail.getText().toString(), binding.Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Sign_Up.this, "User created", Toast.LENGTH_SHORT).show();
                                String userID = mAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("A6 Events' App").document("Users").collection(Gender).document(userID);
                                DocumentReference documentReference = fStore.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Gender", Gender);
                                user.put("First_Name", toStringFirst_Name);
                                user.put("Last_Name", toStringLast_Name);
                                user.put("Birthdate", toStringBirthdate);
                                user.put("Email", toStringEmail);
                                user.put("Password", toStringPassword);

                                user.put("Address", "");
                                user.put("Job", "");
                                user.put("Education", "");
                                user.put("Description", "");

//                            fn = First_Name;


                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                    }
                                });
//                            Intent intent = new Intent(Sign_Up.this, ProfileInfo.class);
//                            intent.putExtra("fn", fn);
//                            startActivity(intent);
                                startActivity(new Intent(getApplicationContext(), ProfileInfo.class));
                                binding.progressBar.setVisibility(View.VISIBLE);

                            }else{
                                Toast.makeText(Sign_Up.this, "Sign up error", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }





            }
        });

    }



    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        String birthDate = DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(calendar.getTime());
        binding.BirthdatePicker.setText(birthDate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}