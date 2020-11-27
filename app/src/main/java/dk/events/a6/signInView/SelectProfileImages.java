package dk.events.a6.signInView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;
import dk.events.a6.activities.MainActivity;
import dk.events.a6.databinding.ActivitySelectProfileImagesBinding;
import dk.events.a6.profileView.AddImages;
import dk.events.a6.profileView.MyAccount;
import dk.events.a6.profileView.ViewProfilePicture;

public class SelectProfileImages extends AppCompatActivity {
    private ActivitySelectProfileImagesBinding binding;
    private FirebaseFirestore fStore;
    private StorageReference storageReference;
    private Boolean imageStatus;
    private FirebaseAuth mAuth;
    private String userID;
    private AddImages addImages;
    private ImageView imageView;
    private int count = 0;

    String First_Name   = "";
    String Last_Name    = "";
    String Gender       = "";
    String Email        = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_images);

        binding = ActivitySelectProfileImagesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        imageStatus = false;
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        addImages = new AddImages();



        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                First_Name = value.getString("First_Name");
                Last_Name = value.getString("Last_Name");
                Gender = value.getString("Gender");
                Email = value.getString("Email");
            }
        });







        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageStatus){
                    Intent intent = new Intent(SelectProfileImages.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }else Toast.makeText(SelectProfileImages.this, "At least, Profile pitcure must be selected", Toast.LENGTH_SHORT).show();
                return;

            }
        });

        binding.UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

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


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "Profile image", SelectProfileImages.this, binding.UserImage);
            } imageStatus = true;
        }else if (requestCode == 1001){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 1", SelectProfileImages.this, binding.UserImage1);
            }
        }else if (requestCode == 1002){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 2", SelectProfileImages.this, binding.UserImage2);
            }
        }else if (requestCode == 1003){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 3", SelectProfileImages.this, binding.UserImage3);
            }
        }else if (requestCode == 1004){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 4", SelectProfileImages.this, binding.UserImage4);
            }
        }else if (requestCode == 1005){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 5", SelectProfileImages.this, binding.UserImage5);
            }
        }else if (requestCode == 1006){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                addImages.uploadeImageToFirebase(imageUri, "image 6", SelectProfileImages.this, binding.UserImage6);
            }
        }
    }

//    @Override
//    public void onClick(View v) {
//        imageView = findViewById(imageView.getId());
//        count++;
//    }

}




