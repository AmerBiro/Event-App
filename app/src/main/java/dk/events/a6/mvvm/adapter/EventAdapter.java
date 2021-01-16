package dk.events.a6.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.mvvm.model.EventModel;

public class EventAdapter extends RecyclerView.Adapter <EventAdapter.EventViewHolder> {

    private List<EventModel> eventModels;
    private OnEventItemClicked onEventItemClicked;

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

//        if (name.length()>15){
//            name = name.substring(0, 15);
//            name = name + "...";
//        }

        holder.name.setText(name);

        Picasso
                .get()
                .load(event_image)
                .fit()
                .into(holder.event_image);

        Picasso
                .get()
                .load(creator_iamge)
                .fit()
                .into(holder.creator_image);

    }

    @Override
    public int getItemCount() {
        if(eventModels == null){
            return 0;
        } else {
            return eventModels.size();
        }
    }


    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView event_image, creator_image;
        private TextView name;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.single_item_event_image);
            creator_image = itemView.findViewById(R.id.single_item_event_creator_image);
            name = itemView.findViewById(R.id.single_item_event_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onEventItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnEventItemClicked {
        public void onItemClicked(int position);
    }

}