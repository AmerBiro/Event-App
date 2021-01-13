package dk.events.a6.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountOverviewBinding;
import dk.events.a6.mvvm.UserModel;
import dk.events.a6.mvvm.image_collections.ImageCollectionAdapter;
import dk.events.a6.mvvm.image_collections.ImageCollectionModel;
import dk.events.a6.mvvm.image_collections.ImageCollectionViewModel;

import static dk.events.a6.activities.MainActivity.TAG;

public class Overview extends Fragment implements ImageCollectionAdapter.OnImageCollectionItemClicked {

    private @NonNull
    AccountOverviewBinding
            binding;
    private NavController controller;
    private DocumentReference reference;
    private ViewPager2 recyclerView;
    private ImageCollectionViewModel imageCollectionViewModel;
    private UserModel userModel;
    private ImageCollectionAdapter adapter;
    private String userId;
    private String first_name, last_name, date_of_birth, gender, address, education, job, description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountOverviewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = OverviewArgs.fromBundle(getArguments()).getUserId();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in Overview: " + userId);
        recyclerViewSetup();
        getUserData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageCollectionViewModel = new ViewModelProvider(getActivity()).get(ImageCollectionViewModel.class);
        imageCollectionViewModel.getImageCollectionModelData().observe(getViewLifecycleOwner(), new Observer<List<ImageCollectionModel>>() {
            @Override
            public void onChanged(List<ImageCollectionModel> imageCollectionModels) {
                Log.d(TAG, "onChanged: " + imageCollectionModels.get(0).getNumber());
                Log.d(TAG, "onChanged: " + imageCollectionModels.get(0).getImage_url());
                adapter.setImageCollectionModels(imageCollectionModels);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onItemClicked(int position) {
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new ImageCollectionAdapter(this);
        recyclerView.setAdapter(adapter);
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
                date_of_birth = userModel.getDate_of_birth();
                gender = userModel.getGender();
                address = userModel.getAddress();
                education = userModel.getEducation();
                job = userModel.getJob();
                description = userModel.getDescription();

                binding.name.setText(first_name + " " + last_name);
                binding.gender.setText(gender);
                binding.dateOfBirth.setText(date_of_birth);
                binding.address.setText(address);
                binding.education.setText(education);
                binding.job.setText(job);
                binding.description.setText(description);

            }
        });
    }
}