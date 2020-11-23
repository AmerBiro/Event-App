package dk.events.a6.profileView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;

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
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbarevents));

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