package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import dk.events.a6.MainActivity;
import dk.events.a6.R;

public class MyAccount extends AppCompatActivity implements View.OnClickListener {

    ImageButton back_arrow_account;
    LinearLayout vis_profil,rediger,indstillinger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        vis_profil=findViewById(R.id.vis_profile_layout);
        rediger=findViewById(R.id.rediger);
        indstillinger=findViewById(R.id.indstillinger);

        vis_profil.setOnClickListener(this);
        rediger.setOnClickListener(this);
        indstillinger.setOnClickListener(this);

        back_arrow_account = findViewById(R.id.back_arrow_account);
        back_arrow_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.vis_profile_layout:
                Toast.makeText(getApplicationContext(),"View profile",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rediger:
                Toast.makeText(getApplicationContext(),"Edit profile",Toast.LENGTH_SHORT).show();
                intent=new Intent(MyAccount.this,EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.indstillinger:
                Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_SHORT).show();
                intent=new Intent(MyAccount.this,ProfileSettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

