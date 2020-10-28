package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.models.MyEvents;

public class ViewProfileActivity extends AppCompatActivity {

    private List<ProfileImage> profileImageList;
    private ProfilePhotoAdapter profilePhotoAdapter;
    private ViewPager2 viewPager2_profile_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        viewPager2_profile_view = findViewById(R.id.id_viewpager2_profile_view);

        profileImageList = new ArrayList<>();
        profileImageList.add(new ProfileImage(R.drawable.profile2));
        profileImageList.add(new ProfileImage(R.drawable.profile3));
        profileImageList.add(new ProfileImage(R.drawable.profile4));




        profilePhotoAdapter = new ProfilePhotoAdapter(profileImageList,this);
        viewPager2_profile_view.setAdapter(profilePhotoAdapter);

    }


}