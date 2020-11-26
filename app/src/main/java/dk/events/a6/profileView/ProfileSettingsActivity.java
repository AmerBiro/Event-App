package dk.events.a6.profileView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import dk.events.a6.R;
import dk.events.a6.activities.MainActivity;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.databinding.ActivityProfileSettingsBinding;
import dk.events.a6.databinding.ActivitySignUpBinding;
import dk.events.a6.signInView.Registeration;

public class ProfileSettingsActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private ActivityProfileSettingsBinding binding;
    private Registeration registeration;


    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private StorageReference storageReference;
    private FirebaseUser user;
    private String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


        binding = ActivityProfileSettingsBinding.inflate(getLayoutInflater());
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
                userEmail = value.getString("Email");
            }
        });


        binding.buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingsActivity.this);
                builder.setMessage("Are you sure you want to delete the following profile " + userEmail + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileSettingsActivity.this, "The following profile: " + userEmail +  " has been deleted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Registeration.class));
                                        finish();
                                        return;
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileSettingsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                ProfileSettingsActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();alert.show();
            }
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    signOutEmail();
                }
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ProfileSettingsActivity.this);
                if (account != null){
                    signOutGmail();
                    Intent intent = new Intent(ProfileSettingsActivity.this, Registeration.class);
                    startActivity(intent);
                }
            }
        });

        binding.idBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void signOutEmail() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(ProfileSettingsActivity.this, Registeration.class);
        Toast.makeText(this, "Email signed out", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(ProfileSettingsActivity.this, Registeration.class);
        Toast.makeText(this, "Google account signed out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
        return;
    }



}