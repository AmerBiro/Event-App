package dk.events.a6.profileView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityViewProfileBinding;

public class ViewProfileActivity extends AppCompatActivity {

    private List<ProfileImage> profileImageList;
    private ProfilePhotoAdapter profilePhotoAdapter;
    private ActivityViewProfileBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbarevents));

        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                binding.FullNameAge.setText(
                        value.getString("First_Name") + " " +
                        value.getString("Last_Name") + ", " +
                        value.getString("Birthdate"));
                binding.Gender.setText(value.getString("Gender"));
                binding.Email.setText(value.getString("Email"));
            }
        });

        profileImageList = new ArrayList<>();
        profileImageList.add(new ProfileImage(R.drawable.profile2));
        profileImageList.add(new ProfileImage(R.drawable.profile3));
        profileImageList.add(new ProfileImage(R.drawable.profile4));





        profilePhotoAdapter = new ProfilePhotoAdapter(profileImageList,this);
        binding.idViewpager2ProfileView.setAdapter(profilePhotoAdapter);


        binding.idViewpager2ProfileView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.numberOfImageText.setText((position + 1 ) + " /" + profilePhotoAdapter.getItemCount());
            }
        });


    }


}