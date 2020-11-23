package dk.events.a6.signInView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import com.example.eventsapp.functions.DatePicker;
import dk.events.a6.R;
import dk.events.a6.databinding.ActivitySignUpBinding;
import dk.events.a6.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ActivitySignUpBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

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

        binding.idDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePicker();
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });


        binding.idButtonSignUpBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up.this, Registeration.class);
                startActivity(intent);
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(Sign_Up.this, MainActivity.class );
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        binding.idButtonSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = binding.radioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(selected);
                if (radioButton.getText() == null){
                    return;
                }


                final String email = binding.idEditTextSignUpEmail.getText().toString();
                final String password = binding.idEditTextSignUpPassword.getText().toString();
                final String name = binding.idEditTextSignUpFirstName.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(Sign_Up.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }else{
                            String userID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child(radioButton.getText().toString()).child(userID).child("name");
                            currentUserDB.setValue(name);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        String birthDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        binding.idDatePicker.setText(birthDate);
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