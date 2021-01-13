package user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.R;

import static dk.events.a6.activities.MainActivity.TAG;

public class ImageHandler {
    private StorageReference accountAvatarRef, accountImageCollections;
    private DocumentReference documentReference;
    private String image_url;

    public void uploadImageToFirebase(Intent data, Activity activity,
                                      String userId, int imageStatus,
                                      ImageView imageView, ProgressBar progressBar) {
        Uri imageUri = data.getData();

        String imageName;
        if (imageStatus == 0) {
            progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, "onSuccess: " + "Image status: " + imageStatus);
            accountAvatarRef = FirebaseStorage.getInstance().getReference().child("/user/" + userId + "/Account Image Avatar.jpg");
            accountAvatarRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: " + "Uploading account image avatar successfully to storage");
                    accountAvatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "onSuccess: " + "Downloading account image url successfully" + image_url);

                            image_url = uri.toString();
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(userId);
                            Map<String, Object> accountAvatar = new HashMap<>();
                            accountAvatar.put("image_url_account", image_url);

                            documentReference.update(accountAvatar).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + "Uploading account image url successfully: ");

                                    Glide
                                            .with(activity)
                                            .load(uri)
                                            .centerCrop()
                                            .placeholder(R.drawable.account)
                                            .into(imageView);

                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity, "Failed uploading image avatar", 0).show();
                }
            });
        } else {
            Log.d(TAG, "onSuccess: " + "Image status: " + imageStatus);

            imageName = "Account Image" + imageStatus + ".jpg";
            accountImageCollections = FirebaseStorage.getInstance().getReference()
                    .child("/user/" + userId + "/Image" + imageStatus + ".jpg");
            accountImageCollections.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: " + "Uploading account image collection successfully to storage");

                    accountImageCollections.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "onSuccess: " + "Downloading account image collection url successfully" + image_url);

                            image_url = uri.toString();

                            documentReference = FirebaseFirestore.getInstance()
                                    .collection("user").document(userId)
                                    .collection("image collection").document("image" + imageStatus);

                            Map<String, Object> images = new HashMap<>();
                            images.put("image_url", image_url);
                            images.put("number", imageStatus);
                            documentReference.set(images).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + "Uploading account image collection url successfully: ");

                                    Glide
                                            .with(activity)
                                            .load(uri)
                                            .centerCrop()
                                            .placeholder(R.drawable.account)
                                            .into(imageView);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "Failed uploading image collection", 0).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
