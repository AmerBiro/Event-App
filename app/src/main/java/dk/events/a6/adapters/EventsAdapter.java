package dk.events.a6.adapters;

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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import dk.events.a6.activities.Event_Content;
import dk.events.a6.R;
import dk.events.a6.models.MyEvent;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    Context context;
    List<MyEvent> myEventList;
    FirebaseStorage firebaseStorage;


    public EventsAdapter(Context context, FirebaseStorage firebaseStorage, List<MyEvent> myEvents) {
        this.context = context;
        this.firebaseStorage = firebaseStorage;
        this.myEventList = myEvents;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_view, parent,false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, final int position) {
        MyEvent myEvent = myEventList.get(position);
        //holder.imageViewEventBackground.setImageResource(myEvent.getEvent_background());
        //holder.imageView_EventAvatar.setImageResource(myEvent.getEvent_avatar());
        //holder.event_title.setText(myEvents.getEvent_title());
        //holder.event_distance.setText(myEvents.getEvent_distance());
        holder.event_title.setText(myEvent.getEvent_title());
        holder.event_distance.setText(myEvent.getEvent_distance());

        // https://stackoverflow.com/questions/48762263/using-firebase-storage-image-with-glide
        // the important part is: Once you have created an AppGlideModule class and done a clean build, you can use GlideApp to load a StorageReference into an ImageView
        // and remember the @GlideModule annotation

        GlideApp.with(context)
                .load(firebaseStorage.getReference(myEvent.getImageLocation()))
                .thumbnail(0.5f)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.centerCrop()
                //.transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView_EventBackground);


        holder.events_view_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Event_Content.class);
                MyEvent myEvent = myEventList.get(position);
                intent.putExtra("event_background_data", myEvent.getEvent_background());
                context.startActivity(intent);
            }
        });

       /* if (user != null){
            // restore profile image
            StorageReference userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/Profile image.jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(holder.imageView_EventAvatar);
                }
            });
        }

        */





    }

    @Override
    public int getItemCount() {
        return myEventList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_EventBackground;
        ImageView imageView_EventAvatar;
        TextView event_title;
        TextView event_distance;
        ConstraintLayout events_view_content;




        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_EventBackground = itemView.findViewById(R.id.id_event_background_content);
            imageView_EventAvatar = itemView.findViewById(R.id.id_event_avatar);
            event_title = itemView.findViewById(R.id.id_event_title);
            event_distance = itemView.findViewById(R.id.id_event_distance);
            events_view_content = itemView.findViewById(R.id.id_event_view);
        }
    }
}
