package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import dk.events.a6.R;

public class MyAccount extends AppCompatActivity implements View.OnClickListener {


    LinearLayout view_profile_layout,edit_profile_layout,settings_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        view_profile_layout=findViewById(R.id.view_profile_layout);
        edit_profile_layout=findViewById(R.id.edit_profile_layout);
        settings_layout=findViewById(R.id.settings_layout);

        view_profile_layout.setOnClickListener(this);
        edit_profile_layout.setOnClickListener(this);
        settings_layout.setOnClickListener(this);

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

