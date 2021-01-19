package dk.events.a6.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.databinding.EventEventsCreatorBinding;
import dk.events.a6.mvvm.adapter.ImageCollectionAdapter;
import dk.events.a6.mvvm.model.ImageCollectionModel;
import dk.events.a6.mvvm.model.UserModel;
import dk.events.a6.mvvm.viewmodel.ImageCollectionViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

import static android.content.ContentValues.TAG;


public class EventsCreator extends Fragment implements ImageCollectionAdapter.OnImageCollectionItemClicked {

    private @NonNull
    EventEventsCreatorBinding
     binding;
    private NavController controller;
    private DocumentReference reference;
    private ViewPager2 viewpager2;
    private ImageCollectionAdapter adapter;
    private List<ImageCollectionModel> imageCollectionModels;
    private UserModel userModel;
    private String creator_Id;
    private String first_name, last_name, date_of_birth, gender, address, education, job, description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EventEventsCreatorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        creator_Id = EventsCreatorArgs.fromBundle(getArguments()).getCreatorId();
        Log.d(TAG, "onViewCreated: " + "creator_Id: " +  creator_Id);
        viewpager2Setup();
        getImageCollection();
        getEventCreatorData();
    }


    private void viewpager2Setup() {
        viewpager2 = binding.viewpager2;
        adapter = new ImageCollectionAdapter(this);
        viewpager2.setAdapter(adapter);
    }

    private void getImageCollection(){
        Query imageCollection = FirebaseFirestore.getInstance()
                .collection("user").document(creator_Id)
                .collection("image collection").orderBy("number");
        imageCollection.addSnapshotListener((value, error) -> {
            imageCollectionModels = value.toObjects(ImageCollectionModel.class);
            Log.d(TAG, "onEvent: " + imageCollectionModels.get(0).getImage_url());
            adapter.setImageCollectionModels(imageCollectionModels);
            adapter.notifyDataSetChanged();
        });
    }

    private void getEventCreatorData(){
        DocumentReference eventCreatorData = FirebaseFirestore.getInstance()
                .collection("user").document(creator_Id);
        eventCreatorData.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userModel = value.toObject(UserModel.class);
                binding.eventCreatorName.setText(userModel.getFirst_name() + " " + userModel.getLast_name());
                binding.eventCreatorGender.setText(userModel.getGender());
                binding.eventCreatorDateOfBirth.setText(userModel.getDate_of_birth());
                binding.eventCreatorAddress.setText(userModel.getAddress());
                binding.eventCreatorEducation.setText(userModel.getEducation());
                binding.eventCreatorJob.setText(userModel.getJob());
                binding.eventCreatorDescription.setText(userModel.getDescription());
            }
        });

    }




    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onItemClicked(int position) {

    }


}