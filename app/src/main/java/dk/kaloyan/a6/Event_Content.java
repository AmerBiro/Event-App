package com.example.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class Event_Content extends AppCompatActivity {

    private ImageView event_background_ImageView;
    private int event_background_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__content);

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