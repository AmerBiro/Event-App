package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.databinding.ActivityProfileSettingsBinding;
import dk.events.a6.databinding.ActivitySignUpBinding;
import dk.events.a6.signInView.Registeration;

public class ProfileSettingsActivity extends AppCompatActivity {

    private ActivityProfileSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        binding = ActivityProfileSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Delete Account",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void signOut(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(ProfileSettingsActivity.this, Registeration.class);
        Toast.makeText(this, "Accout logged out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
        return;
    }
}