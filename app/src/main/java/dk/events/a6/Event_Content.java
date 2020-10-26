package dk.events.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Event_Content extends AppCompatActivity {

    private ImageView event_background_ImageView;
    private int event_background_int;
    private ImageButton id_button_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__content);

        event_background_ImageView = findViewById(R.id.id_event_background_content);
        getData();
        setData();

        id_button_share = findViewById(R.id.id_button_share);

        id_button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "A new event");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


    }

    public void getData(){
        if (getIntent().hasExtra("event_background_data")){
            event_background_int = getIntent().getIntExtra("event_background_data", 1);
        }else Toast.makeText(this, "No data exicts", Toast.LENGTH_LONG).show();
    }

    public void setData(){
        event_background_ImageView.setImageResource(event_background_int);
    }
}