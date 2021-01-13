package dk.events.a6.account.updateprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityChangeEmailPasswordBinding;
import dk.events.a6.account.Account;
import user.UserAuth;

public class Change_Email_Password extends AppCompatActivity {
    private ActivityChangeEmailPasswordBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private StorageReference storageReference;
    private FirebaseUser fuser;
    UserAuth userAuth;


    private String First_Name;
    private String Last_Name;
    private String Birthdate;
    private String Gender;
    private String Email;
    private String Password;
    private String Address;
    private String Job;
    private String Education;
    private String Description;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__email__password);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


        binding = ActivityChangeEmailPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        fuser = mAuth.getCurrentUser();
        userAuth = new UserAuth();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                First_Name = value.getString("First_Name");
                Last_Name = value.getString("Last_Name");
                Birthdate = value.getString("Birthdate");
                Gender = value.getString("Gender");
                Email = value.getString("Email");
                Password = value.getString("Password");
                Address = value.getString("Address");
                Job = value.getString("Job");
                Education = value.getString("Education");
                Description = value.getString("Description");

                if (First_Name == null &&
                        Last_Name == null &&
                        Birthdate == null &&
                        Gender == null &&
                        Email == null &&
                        Address == null &&
                        Job == null &&
                        Education == null &&
                        Description == null){

                    binding.profileEmail.setText("");

                } else{

                    binding.profileEmail.setText(Email);

                }
            }

        });


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        binding.profileEmail.getText().toString().isEmpty()
                ){
                    Toast.makeText(Change_Email_Password.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    fuser.updateEmail(binding.profileEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference dataRef = fStore.collection("Users").document(userID);
                            Map<String, Object> updateUser = new HashMap<>();
                            updateUser.put("First_Name", First_Name);
                            updateUser.put("Last_Name", Last_Name);
                            updateUser.put("Gender", Gender);
                            updateUser.put("Birthdate", Birthdate);
                            updateUser.put("Email", binding.profileEmail.getText().toString());
                            updateUser.put("Password", Password);
                            updateUser.put("Address", Address);
                            updateUser.put("Job", Job);
                            updateUser.put("Education", Education);
                            updateUser.put("Description", Description);
                            dataRef.set(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Change_Email_Password.this, "Email is changed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Change_Email_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(Change_Email_Password.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Change_Email_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });







        binding.ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                user.resetPassword(Change_Email_Password.this, v);
            }
        });

    }
}




