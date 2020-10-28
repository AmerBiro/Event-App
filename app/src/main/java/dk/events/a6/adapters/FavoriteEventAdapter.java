package dk.events.a6.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dk.events.a6.R;
import dk.events.a6.models.FavoriteEventList;

public class FavoriteEventAdapter extends RecyclerView.Adapter<FavoriteEventAdapter.ViewHolder>  {

    private List<FavoriteEventList> favoriteEventLists;
    private Context context;

    public FavoriteEventAdapter(List<FavoriteEventList> favoriteEventLists, Context context) {
        this.favoriteEventLists = favoriteEventLists;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favorite_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteEventAdapter.ViewHolder holder, int position) {
        FavoriteEventList favoriteEventList = favoriteEventLists.get(position);
        holder.name.setText(favoriteEventList.getName());
        holder.eventImage.setImageResource(favoriteEventList.getEvent_image());
        holder.description.setText(favoriteEventList.getDescription());
        holder.profileImage.setImageResource(favoriteEventList.getProfile_image());

    }

    @Override
    public int getItemCount() {
        return favoriteEventLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description;
        private CircleImageView profileImage;
        private ImageView eventImage;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            profileImage=itemView.findViewById(R.id.image_profile);
            name=itemView.findViewById(R.id.name_text);
            description=itemView.findViewById(R.id.description_text);
            eventImage=itemView.findViewById(R.id.event_image);
        }
    }
}
