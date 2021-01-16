package dk.events.a6.mvvm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.mvvm.model.EventModel;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventModel> eventModels;
    private OnEventItemClicked onEventItemClicked;
    private Activity activity;
    private int i;


    public EventAdapter(OnEventItemClicked onEventItemClicked) {
        this.onEventItemClicked = onEventItemClicked;
    }

    public void setEventModels(List<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String event_image, creator_iamge, name;
        name = eventModels.get(position).getName();
        event_image = eventModels.get(position).getImage();
        creator_iamge = eventModels.get(position).getCreator_image();

        if (name.length() > 15) {
            name = name.substring(0, 15);
            name = name + "...";
        }

        holder.name.setText(name);

        if (event_image != null && !event_image.equals("")) {
            Picasso
                    .get()
                    .load(event_image)
                    .fit()
                    .into(holder.event_image);
        }

        if (creator_iamge != null && !creator_iamge.equals("")) {
            Picasso
                    .get()
                    .load(creator_iamge)
                    .fit()
                    .into(holder.creator_image);
        }

        holder.share.setOnClickListener(v -> {
            String image, names, costs, address, date, time, age_range, type, description;
            String creator_image, creator_name, creator_gender, creator_age;
            String distance;

            image = eventModels.get(position).getImage();
            names = eventModels.get(position).getName();
            costs = eventModels.get(position).getCost();
            address = eventModels.get(position).getAddress();
            date = eventModels.get(position).getDate();
            time = eventModels.get(position).getTime();
            age_range = eventModels.get(position).getAge_range();
            type = eventModels.get(position).getType();
            description = eventModels.get(position).getDescription();

            creator_image = eventModels.get(position).getCreator_image();
            creator_name = eventModels.get(position).getCreator_name();
            creator_gender = eventModels.get(position).getCreator_gender();
            creator_age = eventModels.get(position).getCreator_age();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, image + "\n\nBy:\n" +

//                    "Creator Image: " + creator_image + "\n" +
                    "Creator Name: " + creator_name + "\n" +
                    "Creator Sex: " + creator_gender + "\n" +
                    "Creator Age: " + creator_age + "\n" + "\n" +

                    "Details\n" +

                    "Event's Name: " + names + "\n" +
                    "Event's Cost: " + costs + "\n" +
                    "Event's Location: " + address + "\n" +
                    "Event's Date: " + date + "\n" +
                    "Event's Time: " + time + "\n" +
                    "Event's Age Range: " + age_range + "\n" +
                    "Event's Type: " + type + "\n" +
                    "Event's Description: " + description);
            intent.setType("text/plain");
            this.activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (eventModels == null) {
            return 0;
        } else {
            return eventModels.size();
        }
    }


    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView event_image, creator_image;
        private TextView name;
        private ImageButton share;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.single_item_event_image);
            creator_image = itemView.findViewById(R.id.single_item_event_creator_image);
            name = itemView.findViewById(R.id.single_item_event_name);
            share = itemView.findViewById(R.id.single_item_event_share);

            itemView.setOnClickListener(this);

            i = getAdapterPosition();

        }

        @Override
        public void onClick(View v) {
            onEventItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnEventItemClicked {
        public void onItemClicked(int position);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}