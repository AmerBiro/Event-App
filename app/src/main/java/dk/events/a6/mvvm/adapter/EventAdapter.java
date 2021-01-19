package dk.events.a6.mvvm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

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

        if (name.length() > 15 && !name.trim().isEmpty()) {
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

    }

    @Override
    public int getItemCount() {
        if (eventModels == null) {
            return 0;
        } else {
            return eventModels.size();
        }
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {

        private ImageView event_image, creator_image;
        private TextView name;
        private ImageButton share;
        private ToggleButton favorite;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.single_item_event_image);
            creator_image = itemView.findViewById(R.id.single_item_event_creator_image);
            name = itemView.findViewById(R.id.single_item_event_name);
            share = itemView.findViewById(R.id.single_item_event_share);
            favorite = itemView.findViewById(R.id.single_item_event_favorite);


            itemView.setOnClickListener(v -> {
                onEventItemClicked.onItemClicked(getAdapterPosition());
            });
            creator_image.setOnClickListener(v ->
                    onEventItemClicked.onAvatarClicked(getAdapterPosition()));
            share.setOnClickListener(v -> {
                onEventItemClicked.onShareButtonClicked(getAdapterPosition());
            });
            favorite.setOnClickListener(v -> {
                onEventItemClicked.onFavoriteButtonClicked(getAdapterPosition());
            });


        }


    }

    public interface OnEventItemClicked {
        public void onItemClicked(int position);
        public void onAvatarClicked(int position);
        public void onShareButtonClicked(int position);
        public void onFavoriteButtonClicked(int position);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}