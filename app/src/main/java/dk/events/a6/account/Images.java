package dk.events.a6.account;


import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountImagesBinding;
import dk.events.a6.mvvm.UserModel;
import dk.events.a6.mvvm.ImageCollectionModel;
import dk.events.a6.mvvm.ImageCollectionViewModel;
import user.ImageHandler;

import static dk.events.a6.activities.MainActivity.TAG;


public class Images extends Fragment implements View.OnClickListener {

    private @NonNull
    AccountImagesBinding
            binding;
    private NavController controller;
    private ImageCollectionViewModel imageCollectionViewModel;
    private ImageCollectionModel imageCollectionModel;
    private UserModel userModel;
    private DocumentReference documentReference;
    private String userId;
    private ImageView [] imageViews;
    private String [] image_url;
    private int position;
    private ImageHandler imageHandler;

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
        image_url = new String[7];
        imageViews = new ImageView[7];

        imageViews[0] = binding.imagesImage0;
        imageViews[1] = binding.imagesImage1;
        imageViews[2] = binding.imagesImage2;
        imageViews[3] = binding.imagesImage3;
        imageViews[4] = binding.imagesImage4;
        imageViews[5] = binding.imagesImage5;
        imageViews[6] = binding.imagesImage6;

        Log.d(TAG, "onSuccess: " + "Receiving userId successfully in Images: " + userId);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageCollectionViewModel = new ViewModelProvider(getActivity()).get(ImageCollectionViewModel.class);
        imageCollectionViewModel.getImageCollectionModelData().observe(getViewLifecycleOwner(), new Observer<List<ImageCollectionModel>>() {
            @Override
            public void onChanged(List<ImageCollectionModel> imageCollectionModels) {
                int size = imageCollectionViewModel.getImageCollectionModelData().getValue().size();
                for (int i = 1; i<=size; i++){
                    image_url[i] = imageCollectionModels.get(i-1).getImage_url();
                }
                for (int i = 1; i <= size; i++) {
                    Glide
                            .with(getActivity())
                            .load(image_url[i])
                            .centerCrop()
                            .into(imageViews[i]);
                }
                documentReference = FirebaseFirestore.getInstance()
                        .collection("user").document(userId);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        userModel = value.toObject(UserModel.class);
                        image_url[0] = userModel.getImage_url_account();
                        Glide
                                .with(getActivity().getApplicationContext())
                                .load(image_url[0])
                                .centerCrop()
                                .into(imageViews[0]);
                    }
                });
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        binding.backArrowImages.setOnClickListener(this);

        imageViews[0].setOnClickListener(this);
        imageViews[1].setOnClickListener(this);
        imageViews[2].setOnClickListener(this);
        imageViews[3].setOnClickListener(this);
        imageViews[4].setOnClickListener(this);
        imageViews[5].setOnClickListener(this);
        imageViews[6].setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow_images:
                ImagesDirections.ActionImagesToAccount action =
                        ImagesDirections.actionImagesToAccount();
                action.setUserId(userId);
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
                break;
            case R.id.images_image0:
                position = 0;
                openGallery(position);
                break;
            case R.id.images_image1:
                position = 1;
                openGallery(position);
                break;
            case R.id.images_image2:
                position = 2;
                openGallery(position);
                break;
            case R.id.images_image3:
                position = 3;
                openGallery(position);
                break;
            case R.id.images_image4:
                position = 4;
                openGallery(position);
                break;
            case R.id.images_image5:
                position = 5;
                openGallery(position);
                break;
            case R.id.images_image6:
                position = 6;
                openGallery(position);
                break;
            default:
        }
    }


    public void openGallery(int requestCode) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == position && resultCode == Activity.RESULT_OK) {
            imageHandler = new ImageHandler(data, getActivity(),
                    userId, position,
                    imageViews[position], binding.progressBar);
            imageHandler.uploadImageToFirebase();
        }
    }




}