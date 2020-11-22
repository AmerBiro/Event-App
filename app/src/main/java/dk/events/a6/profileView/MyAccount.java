package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityMyAccountBinding;

public class MyAccount extends AppCompatActivity implements View.OnClickListener {
    private ActivityMyAccountBinding binding;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    LinearLayout view_profile_layout,edit_profile_layout,settings_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        binding = ActivityMyAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE);
            }
        });

        view_profile_layout=findViewById(R.id.view_profile_layout);
        edit_profile_layout=findViewById(R.id.edit_profile_layout);
        settings_layout=findViewById(R.id.settings_layout);

        view_profile_layout.setOnClickListener(this);
        edit_profile_layout.setOnClickListener(this);
        settings_layout.setOnClickListener(this);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            binding.profileName.setText(personName);
            binding.personEmail.setText(personEmail);
            binding.personId.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(binding.imageProfile);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.imageProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.view_profile_layout:
                Toast.makeText(getApplicationContext(),"View profile",Toast.LENGTH_SHORT).show();
                intent=new Intent(MyAccount.this,ViewProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_profile_layout:
                Toast.makeText(getApplicationContext(),"Edit profile",Toast.LENGTH_SHORT).show();
                intent=new Intent(MyAccount.this,EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_layout:
                Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_SHORT).show();
                intent=new Intent(MyAccount.this,ProfileSettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

