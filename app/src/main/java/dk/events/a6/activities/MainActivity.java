package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.adapters.EventsAdapter;
import dk.events.a6.android.usecases.createevent.CreateEventActivityView;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.models.MyEvents;
import dk.events.a6.profileView.MyAccount;
import dk.events.a6.signInView.Registeration;
import dk.eventslib.entities.Event;
import dk.eventslib.gateways.EventGatewayInMemory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ViewPager2 viewpager2_events_view;
    private List<MyEvents> myEventsList;
    private EventsAdapter eventsAdapter;
    private FirebaseUser user;
    private ImageButton button_account, button_chat, button_filter, id_button_share;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbarevents));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        user = FirebaseAuth.getInstance().getCurrentUser();

        viewpager2_events_view = findViewById(R.id.id_viewpager2_events_view);


        binding.idButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "A new event");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        binding.idButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null || account != null){

                }
                else {
                    binding.idButtonFavorite.setEnabled(false);
                    Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });



        binding.idButtonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null || account != null){
                    Intent intent = new Intent(MainActivity.this, MyAccount.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

         account = GoogleSignIn.getLastSignedInAccount(this);
        binding.idButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null || account != null){
                    Intent intent = new Intent(MainActivity.this, CreateEventActivityView.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        binding.idButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, MyFilter.class);
                    startActivity(intent);
            }
        });



        myEventsList = new  ArrayList<>();
        addNewEventIfAny();
        myEventsList.add(new MyEvents(R.drawable.event_background_01, R.drawable.event_avatar_01, "Tivoli", "Anonce, indenfor 3 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_02, R.drawable.event_avatar_02, "Cykeltur", "Anonce, indenfor 7 km"));
        myEventsList.add(new MyEvents(R.drawable.event_background_03, R.drawable.event_avatar_03, "Søndags-is :)", "Anonce, indenfor 1 km"));


        eventsAdapter = new EventsAdapter(this, myEventsList);
        viewpager2_events_view.setAdapter(eventsAdapter);

    }

    private void addNewEventIfAny() {
        EventGatewayInMemory inMemory = new EventGatewayInMemory();
        List<Event> events = inMemory.getEvents();
        if(events != null && events.size() > 0){
            Event e = events.get(0);
            myEventsList.add(new MyEvents(R.drawable.ic_baseline_image_not_supported_24, R.drawable.ic_baseline_image_not_supported_24, e.getTitle(), e.getDescription()));
        }
    }

    public void ArrowBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        addNewEventIfAny();
    }
}