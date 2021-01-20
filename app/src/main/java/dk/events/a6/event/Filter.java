package dk.events.a6.event;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

import dk.events.a6.R;
import dk.events.a6.mvvm.adapter.EventAdapter;
import dk.events.a6.mvvm.model.EventModel;

public class Filter implements View.OnClickListener {

    private String eventType;
    private ImageView all_event, sport, entertainment, food, music, culture, get_smarter, free;
    private Activity activity;
    private TextView textView;
    private Dialog dialog;
    private EventAdapter adapter;


    public Filter(Activity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void setAdapter(EventAdapter adapter) {
        this.adapter = adapter;
    }

    public void setFilter() {
        this.dialog = new Dialog(this.activity);
        dialog.setContentView(R.layout.event_event_filter_view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.event_type_anim;
        dialog.show();

        this.all_event = dialog.findViewById(R.id.filter_all_events);
        this.sport = dialog.findViewById(R.id.filter_sport);
        this.entertainment = dialog.findViewById(R.id.filter_entertainment);
        this.food = dialog.findViewById(R.id.filter_food_drink);
        this.music = dialog.findViewById(R.id.filter_music);
        this.culture = dialog.findViewById(R.id.filter_culture);
        this.get_smarter = dialog.findViewById(R.id.filter_get_smarter);
        this.free = dialog.findViewById(R.id.filter_free);

        all_event.setOnClickListener(this);
        sport.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        food.setOnClickListener(this);
        music.setOnClickListener(this);
        culture.setOnClickListener(this);
        get_smarter.setOnClickListener(this);
        free.setOnClickListener(this);
    }


    public void filterSetup(){
        Query query;
        query = FirebaseFirestore.getInstance()
                .collection("event")
                .orderBy("type")
                .startAt(eventType)
                .endAt(eventType + "\uf8ff");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<EventModel> eventModels = value.toObjects(EventModel.class);
                adapter.setEventModels(eventModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void allEvents(){
        Query query;
        query = FirebaseFirestore.getInstance()
                .collection("event");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<EventModel> eventModels = value.toObjects(EventModel.class);
                adapter.setEventModels(eventModels);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_all_events:
                eventType = "All";
                allEvents();
                textView.setVisibility(View.INVISIBLE);
                textView.setText(eventType);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_sport:
                eventType = "Sport";
                filterSetup();
                textView.setVisibility(View.VISIBLE);
                textView.setText(eventType);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_entertainment:
                eventType = "Entertainment";
                filterSetup();
                textView.setVisibility(View.VISIBLE);
                textView.setText(eventType);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_food_drink:
                eventType = "Food & Drink";
                filterSetup();
                textView.setVisibility(View.VISIBLE);
                textView.setText(eventType);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_music:
                eventType = "Music & Night Life";
                filterSetup();
                textView.setVisibility(View.VISIBLE);
                textView.setText(eventType);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_culture:
                eventType = "Culture";
                filterSetup();
                textView.setText(eventType);
                textView.setVisibility(View.VISIBLE);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_get_smarter:
                eventType = "Get Smarter";
                filterSetup();
                textView.setText(eventType);
                textView.setVisibility(View.VISIBLE);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            case R.id.filter_free:
                eventType = "Free";
                filterSetup();
                textView.setText(eventType);
                textView.setVisibility(View.VISIBLE);
                this.dialog.cancel();
                Toast.makeText(activity, textView.getText().toString(), 0).show();
                break;
            default:
        }

    }




}
