package dk.kaloyan.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MyAccount extends AppCompatActivity {

    ImageButton back_arrow_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

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


}

