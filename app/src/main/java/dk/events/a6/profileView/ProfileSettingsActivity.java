package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.databinding.ActivityProfileSettingsBinding;
import dk.events.a6.databinding.ActivitySignUpBinding;

public class ProfileSettingsActivity extends AppCompatActivity {

    private ActivityProfileSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        binding = ActivityProfileSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Log out",Toast.LENGTH_SHORT).show();
            }
        });


        binding.buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Delete Account",Toast.LENGTH_SHORT).show();
            }
        });


    }
}