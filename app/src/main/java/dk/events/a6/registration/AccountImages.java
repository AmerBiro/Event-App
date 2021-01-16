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
import dk.events.a6.user.ImageHandler;

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
    private int imagePosition;

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

        userId = AccountImagesArgs.fromBundle(getArguments()).getUserId();
        imageViews = new ImageView[7];
        imageViews[0] = binding.image0;
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

        binding.accountImagesAddImage.setOnClickListener(this);
        imageViews[1].setOnClickListener(this);
        imageViews[2].setOnClickListener(this);
        imageViews[3].setOnClickListener(this);
        imageViews[4].setOnClickListener(this);
        imageViews[5].setOnClickListener(this);
        imageViews[6].setOnClickListener(this);

        binding.arrowNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_next:
                AccountImagesDirections.ActionAccountImagesToEventViewer action =
                        AccountImagesDirections.actionAccountImagesToEventViewer();
                action.setUserId(userId);
                controller.navigate(action);
                break;
            case R.id.account_images_add_image:
                imagePosition = 0;
                openGallery(imagePosition);
                break;
            case R.id.image1:
                imagePosition = 1;
                openGallery(imagePosition);
                break;
            case R.id.image2:
                imagePosition = 2;
                openGallery(imagePosition);
                break;
            case R.id.image3:
                imagePosition = 3;
                openGallery(imagePosition);
                break;
            case R.id.image4:
                imagePosition = 4;
                openGallery(imagePosition);
                break;
            case R.id.image5:
                imagePosition = 5;
                openGallery(imagePosition);
                break;
            case R.id.image6:
                imagePosition = 6;
                openGallery(imagePosition);
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
        if (requestCode == imagePosition && resultCode == Activity.RESULT_OK) {
            imageHandler = new ImageHandler(data, getActivity(),
                    userId, imagePosition,
                    imageViews[imagePosition], binding.progressBar);
            imageHandler.uploadImageToFirebase();
        }
    }

}




