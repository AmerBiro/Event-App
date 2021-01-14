package dk.events.a6.account;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import dk.events.a6.R;
import dk.events.a6.databinding.AccountImagesBinding;
import dk.events.a6.mvvm.UserModel;
import dk.events.a6.mvvm.image_collections.ImageCollectionAdapter;
import dk.events.a6.mvvm.image_collections.ImageCollectionModel;
import dk.events.a6.mvvm.image_collections.ImageCollectionViewModel;


import androidx.annotation.NonNull;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import static dk.events.a6.activities.MainActivity.TAG;

public class Images extends Fragment implements View.OnClickListener, ImageCollectionAdapter.OnImageCollectionItemClicked {

    private @NonNull
    AccountImagesBinding
     binding;
    private NavController controller;
    private String userId;

    private DocumentReference reference;
    private ViewPager2 viewpager2;
    private ImageCollectionViewModel imageCollectionViewModel;
    private UserModel userModel;
    private ImageCollectionAdapter adapter;
    private String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountImagesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = ImagesArgs.fromBundle(getArguments()).getUserId();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in Images: " + userId);
        viewpager2Setup();
        getUserData();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.backArrowImages.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageCollectionViewModel = new ViewModelProvider(getActivity()).get(ImageCollectionViewModel.class);
        imageCollectionViewModel.getImageCollectionModelData().observe(getViewLifecycleOwner(), new Observer<List<ImageCollectionModel>>() {
            @Override
            public void onChanged(List<ImageCollectionModel> imageCollectionModels) {
//                Log.d(TAG, "onChanged: " + imageCollectionModels.get(0).getImage_url());

                adapter.setImageCollectionModels(imageCollectionModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "onItemClicked: " + position);
    }

    private void viewpager2Setup() {
        viewpager2 = binding.viewpager2;
        adapter = new ImageCollectionAdapter(this);
        viewpager2.setAdapter(adapter);
    }

    public void getUserData(){
        reference = FirebaseFirestore.getInstance()
                .collection("user").document(userId);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                UserModel userModel = value.toObject(UserModel.class);
//                Log.d(TAG, "onEvent: " + userModel.getFirst_name());

                image = userModel.getImage_url_account();

                Glide
                        .with(getActivity())
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.account)
                        .into(binding.image);

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_images:
                ImagesDirections.ActionImagesToAccount action =
                        ImagesDirections.actionImagesToAccount();
                action.setUserId(userId);
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
                break;
            default:
        }
    }
}