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


import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountAccountBinding;
import dk.events.a6.mvvm.UserModel;

import static dk.events.a6.activities.MainActivity.TAG;


public class Account extends Fragment implements View.OnClickListener {

    private @NonNull
    AccountAccountBinding
            binding;
    private NavController controller;
    private String userId, first_name, last_name, image;
    private DocumentReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = AccountArgs.fromBundle(getArguments()).getUserId();
        getUserData();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in Account: " + userId);

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.overview.setOnClickListener(this);
        binding.accountImages.setOnClickListener(this);
    }

    public void getUserData(){
        reference = FirebaseFirestore.getInstance()
                .collection("user").document(userId);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                UserModel userModel = value.toObject(UserModel.class);
//                Log.d(TAG, "onEvent: " + userModel.getFirst_name());

                first_name = userModel.getFirst_name();
                last_name = userModel.getLast_name();
                image = userModel.getImage_url_account();
                binding.name.setText(first_name + " " + last_name);

                Glide
                        .with(getActivity())
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.account)
                        .into(binding.imageProfile);


            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.overview:
                AccountDirections.ActionAccountToOverview action =
                        AccountDirections.actionAccountToOverview();
                action.setUserId(userId);
                controller.navigate(action);
                break;
            case R.id.account_images:
                AccountDirections.ActionAccountToImages action1 =
                        AccountDirections.actionAccountToImages();
                action1.setUserId(userId);
                controller.navigate(action1);
                break;
            default:
        }
    }
}

