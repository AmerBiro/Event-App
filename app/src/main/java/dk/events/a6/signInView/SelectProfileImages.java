package dk.events.a6.signInView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;
import dk.events.a6.activities.MainActivity;
import dk.events.a6.databinding.ActivitySelectProfileImagesBinding;

public class SelectProfileImages extends AppCompatActivity {
    private ActivitySelectProfileImagesBinding binding;
    private FirebaseFirestore fStore;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile_images);

        binding = ActivitySelectProfileImagesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        binding.BackArrowow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectProfileImages.this, Sign_Up.class);
                startActivity(intent);
                finish();
            }
        });

        binding.checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectProfileImages.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        binding.UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1001);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
//                binding.UserImage.setImageURI(imageUri);

                uploadeImageToFirebase(imageUri);

            }
        }
    }

    private void uploadeImageToFirebase(Uri imageUri) {
        final StorageReference fileRf = storageReference.child("UserImage.jpg");
        fileRf.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SelectProfileImages.this, "Image has been uploaded", Toast.LENGTH_SHORT).show();
                fileRf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("UserImage", uri);
                        Glide.with(SelectProfileImages.this).load(uri).into(binding.UserImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SelectProfileImages.this, "Failed uploading image!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}