package com.example.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.EventsViewHolder> {

    Context context;
    List<MyEvents> myEventsList;

    public MyEventsAdapter(Context context, List<MyEvents> myEvents) {
        this.context = context;
        this.myEventsList = myEvents;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_view, parent,false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        MyEvents myEvents = myEventsList.get(position);
        holder.event_background.setImageResource(myEvents.getEvent_background());
        holder.event_avatar.setImageResource(myEvents.getEvent_avatar());
        holder.event_title.setText(myEvents.getEvent_title());
        holder.event_distance.setText(myEvents.getEvent_distance());

        holder.events_view_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Event_Content.class);
                MyEvents myEvents = myEventsList.get(position);
                intent.putExtra("event_background_data",myEvents.getEvent_background());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myEventsList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        ImageView event_background , event_avatar;
        TextView event_title, event_distance;
        ConstraintLayout events_view_content;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            event_background = itemView.findViewById(R.id.id_event_background_content);
            event_avatar = itemView.findViewById(R.id.id_event_avatar);
            event_title = itemView.findViewById(R.id.id_event_title);
            event_distance = itemView.findViewById(R.id.id_event_distance);
            events_view_content = itemView.findViewById(R.id.id_event_view);
        }
    }
}
