package dk.events.a6.profileView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityEditProfileBinding;
import dk.events.a6.databinding.ActivityMyAccountBinding;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.FirstName.setEnabled(false);
        binding.LastName.setEnabled(false);
        binding.profileGender.setEnabled(false);
        binding.BirthdatePicker.setEnabled(false);
        binding.profileEmail.setEnabled(false);



        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

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