package dk.events.a6.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountViewPager2Binding;
import dk.events.a6.mvvm.UserModel;
import user.UserDatebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static dk.events.a6.activities.MainActivity.TAG;


public class AccountViewPager2 extends Fragment implements View.OnClickListener {

    private @NonNull
    AccountViewPager2Binding
     binding;
    private NavController controller;
    private String userId;

    private UserModel userModel;
    private DocumentReference userRef;
    private String [] data;
    private EditText[] fields;
    private UserDatebase userDatebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountViewPager2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fields = new EditText[4];
        data = new String[4];
        userDatebase = new UserDatebase(controller, view);
        fields[0] = binding.address;
        fields[1] = binding.education;
        fields[2] = binding.job;
        fields[3] = binding.description;

        Log.d(TAG, "onSuccess: " + "Receiving userId successfully in ViewPager1: " + userId);
        getUserData();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.saveChanges.setOnClickListener(this);
    }

    private void getUserData(){
        userRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userModel = value.toObject(UserModel.class);
                Log.d(TAG, "onEvent: " + userModel.getFirst_name());
                data[0] = userModel.getAddress();
                data[1] = userModel.getEducation();
                data[2] = userModel.getJob();
                data[3] = userModel.getDescription();

                for (int i = 0; i<4; i++){
                    fields[i].setText(data[i]);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_changes:
                binding.saveChanges.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    userDatebase.uploadUserBackgroundInfoToFirebase(
                            userId,
                            binding.address,
                            binding.education,
                            binding.job,
                            binding.description);
                    binding.saveChanges.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            },1500);
            break;
            default:
        }
    }

}