package dk.events.a6.signInView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityProfileInfoBinding;

public class ProfileInfo extends AppCompatActivity {
    private ActivityProfileInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);


        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}