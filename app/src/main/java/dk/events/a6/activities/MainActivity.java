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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.adapters.EventsAdapter;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.models.MyEvents;
import dk.events.a6.profileView.MyAccount;
import dk.eventslib.entities.Event;
import dk.eventslib.gateways.ObservableEventGatewayInMemory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private ActivityMainBinding binding;

    private ViewPager2 viewpager2_events_view;
    private List<MyEvents> myEventsList;
    private EventsAdapter eventsAdapter;
    private FirebaseUser user;
    private ImageButton button_account, button_chat, button_filter, id_button_share;
    GoogleSignInAccount account;
    private FirebaseAuth ve;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbarevents));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        mAuth = FirebaseAuth.getInstance();
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
                ve = FirebaseAuth.getInstance();
                if (user != null || account != null){

                }
                else {
                    Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });



        binding.idButtonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (user!=null){
//                    if ( !user.isEmailVerified()){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("You have not verified your email yet!");
//                        builder.setMessage("Do you want to verify your email?")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(MainActivity.this, "A verification email has been sent to: \n" + user.getEmail(), Toast.LENGTH_SHORT).show();
//                                                mAuth.signOut();
//                                                startActivity(new Intent(getApplicationContext(), Registeration.class));
////                                            MainActivity.this.finish();
//                                                finish();
//                                                return;
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.d(TAG, "onFailure: " + e.getMessage());
//                                                Toast.makeText(MainActivity.this, "Email verification was not sent!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                        Toast.makeText(MainActivity.this, "You cannot see or edit your profile before verifying your email!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        AlertDialog alert = builder.create();alert.show();
//                    }else{
//                        Intent intent = new Intent(MainActivity.this, MyAccount.class);
//                        startActivity(intent);
//                    }
//                }else if (account != null){
//                    Intent intent = new Intent(MainActivity.this, MyGoogleAccount.class);
//                    startActivity(intent);
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("You don't have an account");
//                    builder.setMessage("Do you want to create an account?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    startActivity(new Intent(getApplicationContext(), Sign_Up.class));
//                                    finish();
//                                    return;
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();alert.show();
//                }
                if (user!=null){
                    startActivity(new Intent(getApplicationContext(), MyAccount.class));
                }
            }
        });

         account = GoogleSignIn.getLastSignedInAccount(this);


        binding.idButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null || account != null){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
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
        ObservableEventGatewayInMemory inMemory = new ObservableEventGatewayInMemory();
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