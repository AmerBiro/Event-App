package dk.events.a6.profileView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityEditProfileBinding;
import dk.events.a6.databinding.ActivityMyAccountBinding;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private StorageReference storageReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        binding.FirstName.setEnabled(false);
//        binding.LastName.setEnabled(false);
//        binding.profileGender.setEnabled(false);
//        binding.BirthdatePicker.setEnabled(false);
//        binding.profileEmail.setEnabled(false);



        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String First_Name = value.getString("First_Name");
                String Last_Name = value.getString("Last_Name");
                String Birthdate = value.getString("Birthdate");
                String Gender = value.getString("Gender");
                String Email = value.getString("Email");
                String Address = value.getString("Address");
                String Job = value.getString("Job");
                String Education = value.getString("Education");
                String Description = value.getString("Description");

                if (First_Name == null ||
                        Last_Name == null ||
                        Birthdate == null ||
                        Gender == null ||
                        Email == null ||
                        Address == null ||
                        Job == null ||
                        Education == null ||
                        Description == null){

                    binding.FirstName.setText("");
                    binding.LastName.setText("");
                    binding.profileGender.setText("");
                    binding.BirthdatePicker.setText("");
                    binding.profileEmail.setText("");
                    binding.Address.setText("");
                    binding.Job.setText("");
                    binding.Education.setText("");
                    binding.Description.setText("");

                } else{
                    binding.FirstName.setText(First_Name);
                    binding.LastName.setText(Last_Name);
                    binding.profileGender.setText(Gender);
                    binding.BirthdatePicker.setText(Birthdate);
                    binding.profileEmail.setText(Email);
                    binding.Address.setText(Address);
                    binding.Job.setText(Job);
                    binding.Education.setText(Education);
                    binding.Description.setText(Description);
                }
            }

        });

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (binding.FirstName.getText().toString().isEmpty() ||
                    binding.LastName.getText().toString().isEmpty() ||
                    binding.BirthdatePicker.getText().toString().isEmpty() ||
                    binding.profileGender.getText().toString().isEmpty() ||
                    binding.profileEmail.getText().toString().isEmpty() ||
                    binding.Password.getText().toString().isEmpty() ||
                    binding.Address.getText().toString().isEmpty() ||
                    binding.Job.getText().toString().isEmpty() ||
                    binding.Education.getText().toString().isEmpty() ||
                    binding.Description.getText().toString().isEmpty()
                ){
                    Toast.makeText(EditProfileActivity.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    user.updateEmail(binding.profileEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference dataRef = fStore.collection("Users").document(userID);
                            Map<String, Object> updateUser = new HashMap<>();
                            updateUser.put("First_Name", binding.FirstName.getText().toString());
                            updateUser.put("Last_Name", binding.LastName.getText().toString());
                            updateUser.put("Gender",binding.profileGender.getText().toString() );
                            updateUser.put("Birthdate", binding.BirthdatePicker.getText().toString());
                            updateUser.put("Email", binding.profileEmail.getText().toString());
                            updateUser.put("Password", binding.Password.getText().toString());
                            updateUser.put("Address", binding.Address.getText().toString());
                            updateUser.put("Job", binding.Job.getText().toString());
                            updateUser.put("Education", binding.Education.getText().toString());
                            updateUser.put("Description", binding.Description.getText().toString());
                            dataRef.set(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(EditProfileActivity.this, "Email is changed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyAccount.class));
                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        binding.Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/Profile Picture.jpg");
        userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(EditProfileActivity.this).load(uri).into(binding.imageProfile);
            }
        });
    }
}