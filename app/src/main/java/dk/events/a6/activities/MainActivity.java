package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.adapters.EventsAdapter;
import dk.events.a6.android.MainApplication;
import dk.events.a6.android._Context;
import dk.events.a6.android.usecases.presentevents.PresentEventsPresenterAsyncImpl;
import dk.events.a6.android.usecases.presentevents.PresentEventsPresenterObserver;
import dk.events.a6.android.usecases.presentevents.PresentableEvent;
import dk.events.a6.databinding.ActivityMainBinding;
import dk.events.a6.models.MyEvent;
import dk.events.a6.profileView.MyAccount;
import dk.events.a6.signInView.functions.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "TAG";
    private ActivityMainBinding binding;

    private ViewPager2 viewpager2_events_view;
    private List<MyEvent> myEventList;
    private EventsAdapter eventsAdapter;
    private FirebaseUser fuser;
    private ImageButton button_account, button_chat, button_filter, id_button_share;
    GoogleSignInAccount account;
    private FirebaseAuth ve;
    private FirebaseAuth mAuth;

    private ImageButton imageButtonHome;
    private ImageView imageButtonAccount;
    private ImageView imageButtonShare;
    private ToggleButton toggleButtonFavorite;
    private ImageView imageButtonFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbarevents));

        mAuth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        viewpager2_events_view = findViewById(R.id.id_viewpager2_events_view);

        account = GoogleSignIn.getLastSignedInAccount(this);

        myEventList = new  ArrayList<>();
        //myEventsList.add(new MyEvents(R.drawable.event_background_01, R.drawable.event_avatar_01, "Tivoli", "Anonce, indenfor 3 km"));
        //myEventsList.add(new MyEvents(R.drawable.event_background_02, R.drawable.event_avatar_02, "Cykeltur", "Anonce, indenfor 7 km"));
        //myEventsList.add(new MyEvents(R.drawable.event_background_03, R.drawable.event_avatar_03, "Søndags-is :)", "Anonce, indenfor 1 km"));

        eventsAdapter = new EventsAdapter(this, ((MainApplication)getApplication()).firebaseStorage, myEventList);
        viewpager2_events_view.setAdapter(eventsAdapter);


        ((PresentEventsPresenterAsyncImpl)_Context.presentEventsPresenterAsync).setPresentEventsPresenterObserver( getPresentEventsPresenterObserver() ); //TODO: getPresentEventsPresenterObserver() should do an update here

    }

    private void initialize() {
        imageButtonHome = findViewById(R.id.id_button_home);
        imageButtonAccount = findViewById(R.id.id_button_account);
        imageButtonShare = findViewById(R.id.id_button_share);
        toggleButtonFavorite = findViewById(R.id.id_button_favorite);
        imageButtonFilter = findViewById(R.id.id_button_filter);


        imageButtonHome.setOnClickListener(this);
        imageButtonAccount.setOnClickListener(this);
        imageButtonShare.setOnClickListener(this);
        toggleButtonFavorite.setOnClickListener(this);
        imageButtonFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_button_home){
            if (fuser != null || account != null){
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
            }
        }else if (v.getId() == R.id.id_button_account){
            //user.verifyUser(MainActivity.this);
            if (fuser!=null){
                startActivity(new Intent(getApplicationContext(), MyAccount.class));
            }else{
                Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId() == R.id.id_button_share){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "A new event");
            intent.setType("text/plain");
            startActivity(intent);
        }else if(v.getId() == R.id.id_button_favorite){
            ve = FirebaseAuth.getInstance();
            if (fuser != null || account != null){

            } else {
                Toast.makeText(MainActivity.this, "You have no account!", Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(v.getId() == R.id.id_button_filter){
            Intent intent = new Intent(MainActivity.this, MyFilter.class);
            startActivity(intent);
        }
    }

    @NotNull
    private PresentEventsPresenterObserver getPresentEventsPresenterObserver() {
        return new PresentEventsPresenterObserver() {
            @Override
            public void starting() {
                new Handler(Looper.getMainLooper()).post(()->{

                });
            }

            @Override
            public void pending() {
                new Handler(Looper.getMainLooper()).post(()->{

                });
            }

            @Override
            public void onSuccess(List<PresentableEvent> presentableEvents) {
                new Handler(Looper.getMainLooper()).post(()->{
                    myEventList.clear();
                    for (PresentableEvent pe : presentableEvents){
                        myEventList.add(new MyEvent(pe.title, pe.description, pe.imageLocation));
                    }
                    eventsAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String errorMsg) {
                new Handler(Looper.getMainLooper()).post(()->{

                });
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        _Context.presentEventsController.execute();

    }

}