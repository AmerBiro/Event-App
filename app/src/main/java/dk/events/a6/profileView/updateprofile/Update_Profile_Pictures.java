package dk.events.a6.profileView.updateprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityUpdateProfilePicturesBinding;

public class Update_Profile_Pictures extends AppCompatActivity {
    private ActivityUpdateProfilePicturesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile__pictures);


        binding = ActivityUpdateProfilePicturesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}