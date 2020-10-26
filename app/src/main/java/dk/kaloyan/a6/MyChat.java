package dk.kaloyan.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MyChat extends AppCompatActivity {

    ImageButton back_arrow_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat);

        back_arrow_chat = findViewById(R.id.back_arrow_chat);
        back_arrow_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyChat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}