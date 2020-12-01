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
import com.google.android.gms.tasks.OnFailureListener;
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
    private DocumentReference documentReference;

     private EditText First_Name, Last_Name, Birthdate, Email, Password;


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
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        First_Name = binding.FirstName;
        Last_Name = binding.LastName;
        Birthdate = binding.BirthdatePicker;
        Email = binding.profileEmail;
        Password = binding.Password;


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
                        checker.genderCheck(Sign_Up.this, binding.radioGroup))
                    return;
                else {
                    mAuth.createUserWithEmailAndPassword(checker.getEmail(), checker.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Sign_Up.this, "User created", Toast.LENGTH_SHORT).show();
                                String userID = mAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("A6 Events' App").document("Users").collection(Gender).document(userID);
                                documentReference = fStore.collection("Users").document(userID);

                                Map<String, Object> user = new HashMap<>();
                                user.put("Gender", checker.getGender());
                                user.put("First_Name", checker.getFirstName());
                                user.put("Last_Name", checker.getLastName());
                                user.put("Birthdate", checker.getBirthDate());
                                user.put("Email", checker.getEmail());
                                user.put("Password", checker.getPassword());

                                user.put("Address", "");
                                user.put("Job", "");
                                user.put("Education", "");
                                user.put("Description", "");



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

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_Up.this, "Cannot create an account " + e.getMessage(), Toast.LENGTH_LONG).show();
                            binding.progressBar.setVisibility(View.GONE);
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