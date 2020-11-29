package dk.events.a6.profileView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.profileView.updateprofile.ProfileImage;

public class ProfilePhotoAdapter extends RecyclerView.Adapter<ProfilePhotoAdapter.ViewHolder> {
    private static final String TAG = "ProfilePhotoAdapter";
    List<ProfileImage> profileImageList;
    Context context;

    public ProfilePhotoAdapter(List<ProfileImage> profileImageList, Context context) {
        this.profileImageList = profileImageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfilePhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"OnCreateViewHolder: Called. :)");

        View view = LayoutInflater.from(context).inflate(R.layout.profile_image_itmes, parent,false);
        return new ProfilePhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePhotoAdapter.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: Called. :)");
        ProfileImage profileImage = profileImageList.get(position);
        holder.profilePhoto.setImageResource(profileImage.getImage());

    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profile_image);
        }
    }
}
