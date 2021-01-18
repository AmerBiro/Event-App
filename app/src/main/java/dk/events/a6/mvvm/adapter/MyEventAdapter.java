package dk.events.a6.mvvm.adapter;

import android.app.Activity;
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

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyEventViewHolder> {

    private List<EventModel> eventModels;
    private OnEventItemClicked onEventItemClicked;
    private Activity activity;


    public MyEventAdapter(OnEventItemClicked onEventItemClicked) {
        this.onEventItemClicked = onEventItemClicked;
    }

    public void setEventModels(List<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    @NonNull
    @Override
    public MyEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_my_event, parent, false);
        return new MyEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventViewHolder holder, int position) {
        String image, name, date, time;
        image = eventModels.get(position).getImage();
        name = eventModels.get(position).getName();
        date = eventModels.get(position).getDate();
        time = eventModels.get(position).getTime();

        if (image != null && !image.equals("")) {
            Picasso
                    .get()
                    .load(image)
                    .fit()
                    .into(holder.event_image);
        }

        if (name.length() > 15) {
            name = name.substring(0, 15);
            name = name + "...";
        }

        holder.name.setText(name);
        holder.date_time.setText(date + ", " + time);

    }


    @Override
    public int getItemCount() {
        if (eventModels == null) {
            return 0;
        } else {
            return eventModels.size();
        }
    }


    public class MyEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView event_image;
        private TextView name, date_time;

        public MyEventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_image = itemView.findViewById(R.id.single_item_my_event_image);
            name = itemView.findViewById(R.id.single_item_my_event_name);
            date_time = itemView.findViewById(R.id.single_item_my_event_date_time);

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

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}