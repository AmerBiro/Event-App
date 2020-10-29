package dk.events.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import dk.events.a6.activities.MainActivity;
import dk.events.a6.databinding.ActivityEventContentBinding;

public class Event_Content extends AppCompatActivity {
    private ActivityEventContentBinding binding;

    private ImageView event_background_ImageView;
    private int event_background_int;
   // private ImageButton id_button_share;
    ImageView event_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__content);
        event_content = findViewById(R.id.id_event_background_content);


        binding = ActivityEventContentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.idEventBackgroundContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Event_Content.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.iconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "A new event");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


        binding.wishToJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Dit ønske er sendt",Toast.LENGTH_SHORT).show();
            }
        });


        binding.repostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"REPOST er på vej",Toast.LENGTH_SHORT).show();
            }
        });



        event_background_ImageView = findViewById(R.id.id_event_background_content);
        getData();
        setData();

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