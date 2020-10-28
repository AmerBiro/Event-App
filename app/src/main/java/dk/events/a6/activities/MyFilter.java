package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import dk.events.a6.R;

public class MyFilter extends AppCompatActivity {

    ImageButton back_arrow_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_filter);

        back_arrow_filter = findViewById(R.id.back_arrow_filter);
        back_arrow_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyFilter.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}