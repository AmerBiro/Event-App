package dk.events.a6.profileView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import dk.events.a6.R;
import dk.events.a6.activities.MainActivity;
import dk.events.a6.databinding.ActivityMyAccountBinding;


public class MyAccount extends AppCompatActivity /*implements View.OnClickListener*/ {
    private ActivityMyAccountBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private StorageReference storageReference;
    private String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));

        binding = ActivityMyAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);








        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = mAuth.getCurrentUser().getUid();

        // if signed in via email
        if (user != null){
            // restore profile image
            StorageReference userimage = storageReference.child("UserImage.jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MyAccount.this).load(uri).into(binding.imageProfile);
                }
            });
            // restore profile information
            DocumentReference documentReference = fStore.collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    String First_Name = value.getString("First_Name");
                    String Last_Name = value.getString("Last_Name");
                    String Birthdate = value.getString("Birthdate");
                    if (First_Name == null || Last_Name == null || Birthdate == null)
                        binding.FullNameAge.setText("");
                    else binding.FullNameAge.setText(First_Name + " " + Last_Name + ", " + Birthdate);
                }
            });
        }


        binding.viewProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"View profile",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyAccount.this,ViewProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.editProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Edit profile",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyAccount.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyAccount.this, ProfileSettingsActivity.class);
                startActivity(intent);
            }
        });

        binding.idBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

    }


}

