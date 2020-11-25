package dk.events.a6.profileView.updateprofile;

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
import dk.events.a6.databinding.ActivityUpdatePersonalInformationBinding;
import dk.events.a6.profileView.MyAccount;

        public class Update_Personal_Information extends AppCompatActivity {
            private ActivityUpdatePersonalInformationBinding binding;

            private FirebaseAuth mAuth;
            private FirebaseFirestore fStore;
            private String userID;
            private StorageReference storageReference;
            private FirebaseUser user;

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

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_update__personal__information);
                Window window = this.getWindow();
                window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


                binding = ActivityUpdatePersonalInformationBinding.inflate(getLayoutInflater());
                View view = binding.getRoot();
                setContentView(view);

                mAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();
                userID = mAuth.getCurrentUser().getUid();
                user = mAuth.getCurrentUser();

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

                        if (
                                First_Name == null ||
                                Last_Name == null ||
                                Birthdate == null ||
                                Gender == null ||
                                Email == null ||
                                Password == null ||
                                Address == null ||
                                Job == null ||
                                Education == null ||
                                Description == null
                        ){

                            binding.FirstName.setText("");
                            binding.LastName.setText("");
                            binding.profileGender.setText("");
                            binding.BirthdatePicker.setText("");

                        } else{
                            binding.FirstName.setText(First_Name);
                            binding.LastName.setText(Last_Name);
                            binding.profileGender.setText(Gender);
                            binding.BirthdatePicker.setText(Birthdate);
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
                                binding.FirstName.getText().toString().isEmpty() ||
                                binding.LastName.getText().toString().isEmpty() ||
                                binding.BirthdatePicker.getText().toString().isEmpty() ||
                                binding.profileGender.getText().toString().isEmpty()
                        ){
                            Toast.makeText(dk.events.a6.profileView.updateprofile.Update_Personal_Information.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DocumentReference dataRef = fStore.collection("Users").document(userID);
                                    Map<String, Object> updateUser = new HashMap<>();
                                    updateUser.put("First_Name", binding.FirstName.getText().toString());
                                    updateUser.put("Last_Name", binding.LastName.getText().toString());
                                    updateUser.put("Gender",binding.profileGender.getText().toString() );
                                    updateUser.put("Birthdate", binding.BirthdatePicker.getText().toString());
                                    updateUser.put("Email", Email);
                                    updateUser.put("Password", Password);
                                    updateUser.put("Address", Address);
                                    updateUser.put("Job", Job);
                                    updateUser.put("Education", Education);
                                    updateUser.put("Description", Description);
                                    dataRef.set(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
//                                            Toast.makeText(dk.events.a6.profileView.updateprofile.Update_Personal_Information.this, "Email is changed successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(dk.events.a6.profileView.updateprofile.Update_Personal_Information.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Toast.makeText(dk.events.a6.profileView.updateprofile.Update_Personal_Information.this, "Personal information updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MyAccount.class));
                                    finish();
                                    return;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(dk.events.a6.profileView.updateprofile.Update_Personal_Information.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


            }
        }