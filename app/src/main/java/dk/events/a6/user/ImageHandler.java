package dk.events.a6.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static dk.events.a6.activities.MainActivity.TAG;

public class ImageHandler {
    private StorageReference accountAvatarRef, accountImageCollections, eventImageRef;
    private DocumentReference documentReference;
    private String image_url;

    private Intent data;
    private Activity activity;
    private String userId;
    private int imageStatus;
    ImageView imageView;
    private ProgressBar progressBar;


    public ImageHandler(Intent data, Activity activity, String userId, int imageStatus, ImageView imageView, ProgressBar progressBar) {
        this.data = data;
        this.activity = activity;
        this.userId = userId;
        this.imageStatus = imageStatus;
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    public ImageHandler(Intent data, Activity activity, String userId, int imageStatus) {
        this.data = data;
        this.activity = activity;
        this.userId = userId;
        this.imageStatus = imageStatus;
    }


    public void uploadImageToFirebase() {
        Uri imageUri = this.data.getData();

        String imageName;
        if (this.imageStatus == 0) {
            if (this.progressBar != null) {
                this.progressBar.setVisibility(View.VISIBLE);
            }

            Log.d(TAG, "onSuccess: " + "Image status: " + this.imageStatus);
            accountAvatarRef = FirebaseStorage.getInstance().getReference().child("user/" + userId + "/Account Image Avatar.jpg");
            accountAvatarRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: " + "Uploading account image avatar successfully to storage");
                    accountAvatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "onSuccess: " + "Downloading account image url successfully");

                            image_url = uri.toString();
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(userId);
                            Map<String, Object> accountAvatar = new HashMap<>();

                            accountAvatar.put("image_url_account", image_url);
                            Log.d(TAG, "onSuccess: " + documentReference.getId());

                            documentReference.update(accountAvatar).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + "Uploading account image url successfully: ");

                                    if (imageView != null) {
//                                            Glide
//                                                    .with(activity)
//                                                    .load(uri)
//                                                    .centerCrop()
//                                                    .into(imageView);

                                        Picasso
                                                .get()
                                                .load(uri)
                                                .fit()
                                                .into(imageView);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        return;
                                    }
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
            this.progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, "onSuccess: " + "Image status: " + imageStatus);

            imageName = "Account Image" + imageStatus + ".jpg";
            accountImageCollections = FirebaseStorage.getInstance().getReference()
                    .child("user/" + userId + "/Image" + imageStatus + ".jpg");
            accountImageCollections.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: " + "Uploading account image collection successfully to storage");

                    accountImageCollections.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "onSuccess: " + "Downloading account image collection url successfully");

                            image_url = uri.toString();

                            DocumentReference documentReference = FirebaseFirestore.getInstance()
                                    .collection("user").document(userId)
                                    .collection("image collection").document("image" + imageStatus);

                            Map<String, Object> images = new HashMap<>();
                            images.put("image_url", image_url);
                            images.put("number", imageStatus);
                            documentReference.set(images).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + "Uploading account image collection url successfully: ");

                                    if (imageView != null) {
//                                            Glide
//                                                    .with(activity)
//                                                    .load(uri)
//                                                    .centerCrop()
//                                                    .into(imageView);

                                        Picasso
                                                .get()
                                                .load(uri)
                                                .fit()
                                                .into(imageView);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        return;
                                    }
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


//    public void UploadeEventImage() {
//
//        Uri imageUri = this.data.getData();
//        String imageName;
//        this.progressBar.setVisibility(View.VISIBLE);
//
//        eventImageRef = FirebaseStorage.getInstance().getReference().child("user/" + userId + "/event/Account Image Avatar.jpg");
//        eventImageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "onSuccess: " + "Uploading account image avatar successfully to storage");
//                accountAvatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Log.d(TAG, "onSuccess: " + "Downloading account image url successfully");
//
//                        image_url = uri.toString();
//                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(userId);
//                        Map<String, Object> accountAvatar = new HashMap<>();
//
//                        accountAvatar.put("image_url_account", image_url);
//                        Log.d(TAG, "onSuccess: " + documentReference.getId());
//
//                        documentReference.update(accountAvatar).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "onSuccess: " + "Uploading account image url successfully: ");
//
//                                if (imageView != null) {
////                                            Glide
////                                                    .with(activity)
////                                                    .load(uri)
////                                                    .centerCrop()
////                                                    .into(imageView);
//
//                                    Picasso
//                                            .get()
//                                            .load(uri)
//                                            .fit()
//                                            .into(imageView);
//                                    progressBar.setVisibility(View.INVISIBLE);
//                                    return;
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(activity, "Failed uploading image avatar", 0).show();
//            }
//        });
//    }
}



