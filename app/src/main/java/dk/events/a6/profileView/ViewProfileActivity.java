package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.MainActivity;
import dk.events.a6.R;
import dk.events.a6.models.MyEvents;

public class ViewProfileActivity extends AppCompatActivity {

    private List<ProfileImage> profileImageList;
    private ProfilePhotoAdapter profilePhotoAdapter;
    private ViewPager2 viewPager2_profile_view;
    private TextView number_of_image_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        viewPager2_profile_view = findViewById(R.id.id_viewpager2_profile_view);
        number_of_image_text=findViewById(R.id.number_of_image_text);

        profileImageList = new ArrayList<>();
        profileImageList.add(new ProfileImage(R.drawable.profile2));
        profileImageList.add(new ProfileImage(R.drawable.profile3));
        profileImageList.add(new ProfileImage(R.drawable.profile4));





        profilePhotoAdapter = new ProfilePhotoAdapter(profileImageList,this);
        viewPager2_profile_view.setAdapter(profilePhotoAdapter);


        viewPager2_profile_view.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                number_of_image_text.setText((position + 1 ) + " /" + profilePhotoAdapter.getItemCount());
            }
        });


    }


}