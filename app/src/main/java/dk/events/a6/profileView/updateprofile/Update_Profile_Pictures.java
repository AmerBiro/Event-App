package dk.events.a6.profileView.updateprofile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dk.events.a6.R;
import dk.events.a6.databinding.ActivityUpdateProfilePicturesBinding;
import dk.events.a6.profileView.AddImages;

public class Update_Profile_Pictures extends AppCompatActivity {
    private ActivityUpdateProfilePicturesBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private StorageReference storageReference;
    private String userID;
    private AddImages addImages;
    StorageReference userimage;

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile__pictures);


        binding = ActivityUpdateProfilePicturesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String image1 = "image 1";
        String image2 = "image 2";
        String image3 = "image 3";
        String image4 = "image 4";
        String image5 = "image 5";
        String image6 = "image 6";



        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = mAuth.getCurrentUser().getUid();
        addImages = new AddImages();



        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image1+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage1);
                }
            });
        }

        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image2+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage2);
                }
            });
        }

        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image3+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage3);
                }
            });
        }

        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image4+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage4);
                }
            });
        }

        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image5+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage5);
                }
            });
        }

        // show profile image if signed in via email
        if (user != null){
            // restore profile image
            userimage = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/"+image6+".jpg");
            userimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Update_Profile_Pictures.this).load(uri).into(binding.UserImage6);
                }
            });
        }


        binding.UserImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1001);
            }
        });

        binding.UserImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1002);
            }
        });

        binding.UserImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1003);
            }
        });

        binding.UserImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1004);
            }
        });

        binding.UserImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1005);
            }
        });

        binding.UserImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1006);
            }
        });


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 1", Update_Profile_Pictures.this, binding.UserImage1);
            }
        }else if (requestCode == 1002){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 2", Update_Profile_Pictures.this, binding.UserImage2);
            }
        }else if (requestCode == 1003){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 3", Update_Profile_Pictures.this, binding.UserImage3);
            }
        }else if (requestCode == 1004){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 4", Update_Profile_Pictures.this, binding.UserImage4);
            }
        }else if (requestCode == 1005){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 5", Update_Profile_Pictures.this, binding.UserImage5);
            }
        }else if (requestCode == 1006){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 6", Update_Profile_Pictures.this, binding.UserImage6);
            }
        }
    }
}