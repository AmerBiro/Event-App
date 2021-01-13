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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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


import dk.events.a6.R;
import dk.events.a6.databinding.ActivityMyAccountBinding;
import dk.events.a6.profileView.updateprofile.Change_Email_Password;
import dk.events.a6.profileView.updateprofile.Update_Personal_Information;
import dk.events.a6.profileView.updateprofile.Update_Profile_Background;
import dk.events.a6.profileView.updateprofile.Update_Profile_Pictures;
import dk.events.a6.registration.Registration;
import dk.events.a6.registration.logic.User;


public class MyAccount extends AppCompatActivity /*implements View.OnClickListener*/ {
    private ActivityMyAccountBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private StorageReference storageReference;
    private String userID;
    GoogleSignInClient mGoogleSignInClient;
    User mUser;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));

        binding = ActivityMyAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        mUser = new User();





        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = mAuth.getCurrentUser().getUid();



        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // if signed in via email
        if (user != null){
            // restore profile image
            StorageReference userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/Profile image.jpg");
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
                        binding.profileName.setText("");
                    else binding.profileName.setText(First_Name + " " + Last_Name + ", " + Birthdate);
                }
            });
        }


        binding.viewProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Profile Overview",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
            }
        });


        binding.Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileSettingsActivity.class));
                Toast.makeText(getApplicationContext(),"Personal Settings",Toast.LENGTH_SHORT).show();
            }
        });

        binding.idBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewProfilePicture.class));
                Toast.makeText(getApplicationContext(),"Profile image",Toast.LENGTH_SHORT).show();

            }
        });

        binding.updatePersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Update_Personal_Information.class));
                Toast.makeText(getApplicationContext(),"Update Personal Information",Toast.LENGTH_SHORT).show();
            }
        });

        binding.updateProfileBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Update_Profile_Background.class));
                Toast.makeText(getApplicationContext(),"Update Profile background\n",Toast.LENGTH_SHORT).show();
            }
        });

        binding.updateProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Update_Profile_Pictures.class));
                Toast.makeText(getApplicationContext(),"Update Profile Pictures\n",Toast.LENGTH_SHORT).show();
            }
        });

        binding.changeEmailOrPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Change_Email_Password.class));
                Toast.makeText(getApplicationContext(),"Change Email / Password",Toast.LENGTH_SHORT).show();
            }
        });

        binding.deleteAccountIconId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser.deleteUser(MyAccount.this, Registration.class);
            }
        });


        binding.logOutIconId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser.signOut(MyAccount.this)){
                    afterSignout();
                }

                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MyAccount.this);
                if (account != null){
                    signOutGmail();
                    Intent intent = new Intent(MyAccount.this, Registration.class);
                    startActivity(intent);
                }
            }
        });




    }

    public void afterSignout() {
        Intent intent = new Intent(MyAccount.this, Registration.class);
        startActivity(intent);
        finish();
        return;
    }

    private void signOutGmail() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
        Intent intent = new Intent(MyAccount.this, Registration.class);
        Toast.makeText(this, "Google account signed out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
        return;
    }


}

