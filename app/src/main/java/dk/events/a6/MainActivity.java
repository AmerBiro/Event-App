package dk.events.a6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.adapters.EventsAdapter;
import dk.events.a6.models.MyEvents;
import dk.events.a6.profileView.MyAccount;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewpager2_events_view;
    private List<MyEvents> myEventsList;
    private EventsAdapter eventsAdapter;

    private ImageButton button_account, button_chat, button_filter, id_button_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager2_events_view = findViewById(R.id.id_viewpager2_events_view);

        button_account = findViewById(R.id.id_button_account);
        button_chat = findViewById(R.id.id_button_chat);
        button_filter = findViewById(R.id.id_button_filter);
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






        button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                startActivity(intent);
            }
        });

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyFilter.class);
                startActivity(intent);
            }
        });



        myEventsList = new  ArrayList<>();
        myEventsList.add(new MyEvents(R.drawable.event_background_01, R.drawable.event_avatar_01, "Drink with friends", "Anonce, indenfor 3 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_02, R.drawable.event_avatar_02, "Concert evening", "Anonce, indenfor 7 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_03, R.drawable.event_avatar_03, "Ice cream tour", "Anonce, indenfor 1 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_04, R.drawable.event_avatar_04, "christmas night", "Anonce, indenfor 14 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_05, R.drawable.event_avatar_05, "valentine day", "Anonce, indenfor 6 km"));

        eventsAdapter = new EventsAdapter(this, myEventsList);
        viewpager2_events_view.setAdapter(eventsAdapter);


    }

    public void ArrowBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}