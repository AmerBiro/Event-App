package dk.events.a6.registration;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import dk.events.a6.R;
import dk.events.a6.databinding.RegistrationAccountImagesBinding;
import user.ImageHandler;
import user.UserDatebase;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import static dk.events.a6.activities.MainActivity.TAG;


public class AccountImages extends Fragment implements View.OnClickListener {
    private RegistrationAccountImagesBinding binding;
    private String userId;
    private NavController controller;
    private ImageHandler imageHandler;
    private ImageView [] imageViews;
    private int imageStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationAccountImagesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                Log.d("BACKBUTTON", "Back button clicks");
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        imageHandler = new ImageHandler();
        userId = AccountImagesArgs.fromBundle(getArguments()).getUserId();
        imageViews = new ImageView[7];
        imageViews[0] = binding.avatarImage;
        imageViews[1] = binding.image1;
        imageViews[2] = binding.image2;
        imageViews[3] = binding.image3;
        imageViews[4] = binding.image4;
        imageViews[5] = binding.image5;
        imageViews[6] = binding.image6;
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in AccountImage: " +userId);
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.image0.setOnClickListener(this);
        binding.image1.setOnClickListener(this);
        binding.image2.setOnClickListener(this);
        binding.image3.setOnClickListener(this);
        binding.image4.setOnClickListener(this);
        binding.image5.setOnClickListener(this);
        binding.image6.setOnClickListener(this);

        binding.arrowNext.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_next:
                AccountImagesDirections.ActionAccountImagesToAccount action =
                        AccountImagesDirections.actionAccountImagesToAccount();
                action.setUserId(userId);
                controller.navigate(action);
                break;
            case R.id.image0:
                imageStatus = 0;
                openGallery(imageStatus);
                break;
            case R.id.image1:
                imageStatus = 1;
                openGallery(imageStatus);
                break;
            case R.id.image2:
                imageStatus = 2;
                openGallery(imageStatus);
                break;
            case R.id.image3:
                imageStatus = 3;
                openGallery(imageStatus);
                break;
            case R.id.image4:
                imageStatus = 4;
                openGallery(imageStatus);
                break;
            case R.id.image5:
                imageStatus = 5;
                openGallery(imageStatus);
                break;
            case R.id.image6:
                imageStatus = 6;
                openGallery(imageStatus);
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
        if (requestCode == imageStatus && resultCode == Activity.RESULT_OK) {
            imageHandler.uploadImageToFirebase(data, getActivity(),
                    userId, imageStatus,
                    imageViews[imageStatus], binding.progressBar);
        }
    }
}




