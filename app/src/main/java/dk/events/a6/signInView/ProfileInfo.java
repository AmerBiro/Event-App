package dk.events.a6.signInView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityProfileInfoBinding;

public class ProfileInfo extends AppCompatActivity {
    private ActivityProfileInfoBinding binding;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);


        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        // Puting info into profile's database
        binding.NextArrowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Users = "Users";
                final String Address = binding.Address.getText().toString();
                final String Job = binding.Job.getText().toString();
                final String Education = binding.Education.getText().toString();
                final String Description = binding.Description.getText().toString();
                if (    Address.isEmpty()||
                        Job.isEmpty()||
                        Education.isEmpty()||
                        Description.isEmpty()
                ){
//                    Toast.makeText(ProfileInfo.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//                    return;
                }
                if (user != null){
                    String userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
//                    Intent intent = getIntent();
//                    String fn = intent.getStringExtra("fn");
//                    user.put("First_Name", fn);
                    user.put("Address", Address);
                    user.put("Job", Job);
                    user.put("Education", Education);
                    user.put("Description", Description);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), SelectProfileImages.class));
                }
            }
        });




    }
}