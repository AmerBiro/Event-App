package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import dk.events.a6.MainActivity;
import dk.events.a6.R;

public class EditProfileActivity extends AppCompatActivity {
    ImageButton back_arrow_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        back_arrow_account = findViewById(R.id.back_arrow_account);
        back_arrow_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this,MyAccount.class);
                startActivity(intent);
                finish();
            }
        });

    }
}