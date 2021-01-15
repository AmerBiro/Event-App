package dk.events.a6.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


import dk.events.a6.databinding.AccountViewPager1Binding;
import dk.events.a6.mvvm.UserModel;


import static dk.events.a6.activities.MainActivity.TAG;


public class AccountViewPager1 extends Fragment {

    private @NonNull
    AccountViewPager1Binding
            binding;
    private NavController controller;
    private String userId;
    private UserModel userModel;
    private DocumentReference userRef;
    private String [] data;
    private EditText [] fields;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountViewPager1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fields = new EditText[5];
        data = new String[5];
        fields[0] = binding.firstName;
        fields[1] = binding.lastName;
        fields[2] = binding.dateOfBirth;
        fields[3] = binding.email;
        fields[4] = binding.password;
        for (int i = 0; i<fields.length; i++){
            fields[i].setEnabled(false);
        }
        Log.d(TAG, "onSuccess: " + "Receiving userId successfully in ViewPager1: " + userId);
        getUserData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getUserData(){
        userRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId);
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userModel = value.toObject(UserModel.class);
                Log.d(TAG, "onEvent: " + userModel.getFirst_name());
                data[0] = userModel.getFirst_name();
                data[1] = userModel.getLast_name();
                data[2] = userModel.getDate_of_birth();
                data[3] = userModel.getEmail();
                data[4] = userModel.getImage_url_account();

                for (int i = 0; i<4; i++){
                    fields[i].setText(data[i]);
                }

                Glide
                        .with(getActivity())
                        .load(data[4])
                        .centerCrop()
                        .into(binding.image);

            }
        });
    }



}