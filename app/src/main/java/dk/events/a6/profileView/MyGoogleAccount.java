package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityMyGoogleAccountBinding;
import dk.events.a6.profileView.updateprofile.EditProfileActivity;

public class MyGoogleAccount extends AppCompatActivity {
    private ActivityMyGoogleAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_google_account);


        binding = ActivityMyGoogleAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // íf signed in via gmail
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MyGoogleAccount.this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            Uri personPhoto = account.getPhotoUrl();

            binding.profileName.setText(personName);
            binding.personEmail.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(binding.imageProfile);












            binding.viewProfileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"View profile",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MyGoogleAccount.this,ViewProfileActivity.class);
                    startActivity(intent);
                }
            });

            binding.editProfileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Edit profile",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MyGoogleAccount.this, EditProfileActivity.class);
                    startActivity(intent);
                }
            });

            binding.settingsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MyGoogleAccount.this, ProfileSettingsActivity.class);
                    startActivity(intent);
                }
            });

        }
        binding.idBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}