package dk.events.a6.event;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RepostEvent {

    private String imageId, name, address, date, time, age_range, type, description, distance,
            reposter_id, reposter_image, reposter_name, reposter_gender, reposter_age;
    private CollectionReference eventRef;
    private NavController controller;
    private View view;
    private Activity activity;
    private int cost;

    public RepostEvent(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.activity = activity;
        this.controller = Navigation.findNavController(this.view);
    }

    public void Repost(String imageId,
                       String name, int cost, String address, String date, String time,
                       String age_range, String type, String description, String distance,
                       String reposter_id, String reposter_image, String reposter_name,
                       String reposter_gender, String reposter_age,
                       ImageButton repostButton, ProgressBar repost_progressBar
    ) {
        repostButton.setVisibility(View.INVISIBLE);
        repost_progressBar.setVisibility(View.VISIBLE);

        this.imageId = imageId;
        Log.d(TAG, "onSuccess: " + "Old imageId: " + imageId);

        this.name = name;
        this.cost = cost;
        this.address = address;
        this.date = date;
        this.time = time;
        this.age_range = age_range;
        this.type = type;
        this.description = description;
        this.distance = distance;
        this.reposter_id = reposter_id;
        this.reposter_image = reposter_image;
        this.reposter_name = reposter_name;
        this.reposter_gender = reposter_gender;
        this.reposter_age = reposter_age;

        Map<String, Object> repost = new HashMap<>();

        repost.put("name", this.name);
        repost.put("cost", this.cost);
        repost.put("address", this.address);
        repost.put("date", this.date);
        repost.put("time", this.time);
        repost.put("age_range", this.age_range);
        repost.put("type", this.type);
        repost.put("description", this.description);
        repost.put("distance", this.distance);
        repost.put("creator_id", this.reposter_id);
        repost.put("creator_image", this.reposter_image);
        repost.put("creator_name", this.reposter_name);
        repost.put("creator_gender", this.reposter_gender);
        repost.put("creator_age", this.reposter_age);

        this.eventRef = FirebaseFirestore.getInstance()
                .collection("event");
        this.eventRef.add(repost).addOnCompleteListener(task -> {
            // Successfully Reposting event
            if (task.isSuccessful()) {
                String repostedEventId = task.getResult().getId();
                Log.d(TAG, "onSuccess: " + "Reposting evemt successfully with eventId: " + repostedEventId);

                // Downloading the old image
                StorageReference downloadOldImageRef = FirebaseStorage.getInstance().getReference();
                StorageReference downloadOldImageRef1 = downloadOldImageRef.child("event").child(imageId);

                downloadOldImageRef1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // Successfully downloading the old image
                            Uri downUri = task.getResult();
                            String downloadedImageUrl = downUri.toString();

                            
                            Log.d(TAG, "onSuccess: " + "Downloading old image successfully: " + downloadedImageUrl);

                            StorageReference reference = FirebaseStorage.getInstance().getReference();
                            StorageReference reference1 = reference.child("event/" + repostedEventId);

                            reference1.putFile(downUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()){
                                        // Successfully uploading reposted image
                                        Log.d(TAG, "onSuccess: " + "Successfully uploading reposted image");

                                        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()){
                                                    // Downloading the new image successfully
                                                    String newImage = task.getResult().toString();
                                                    Log.d(TAG, "onSuccess: " + "Successfully downloading image_url");

                                                    DocumentReference reference1 = FirebaseFirestore.getInstance()
                                                            .collection("event").document(repostedEventId);

                                                    Map<String, Object> repostedImage = new HashMap<>();
                                                    repostedImage.put("image", newImage);

                                                    reference1.update(repostedImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                // Successfully updating reposted event data
                                                                Log.d(TAG, "onSuccess: " + "Successfully updating reposted event data");
                                                                repost_progressBar.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(activity, "Repost successfully", 0).show();
//                                                                controller.navigate(action);
                                                            }else{
                                                                // Error updating reposted event data
                                                                Log.d(TAG, "onFailure: " + "Error updating reposted event data: " + task.getException());
                                                                repostButton.setVisibility(View.VISIBLE);
                                                                repost_progressBar.setVisibility(View.INVISIBLE);
                                                                return;
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    // Error the new image successfully
                                                    Log.d(TAG, "onFailure: " + "Error uploading reposted image: " + task.getException());
                                                    repostButton.setVisibility(View.VISIBLE);
                                                    repost_progressBar.setVisibility(View.INVISIBLE);
                                                    return;
                                                }
                                            }
                                        });
                                    }else{
                                        // Error uploading reposted image
                                        Log.d(TAG, "onFailure: " + "Error uploading reposted image: " + task.getException());
                                        repostButton.setVisibility(View.VISIBLE);
                                        repost_progressBar.setVisibility(View.INVISIBLE);
                                        return;
                                    }
                                }
                            });
                        } else {
                            // Error downloading the old image
                            Log.d(TAG, "onFailure: " + "Error downloading the old image: " + task.getException());
                            repostButton.setVisibility(View.VISIBLE);
                            repost_progressBar.setVisibility(View.INVISIBLE);
                            return;
                        }
                    }
                });
            } else {
                // Error Reposting event
                Log.d(TAG, "onFailure: " + "Error reposting an event: " + task.getException());
                repostButton.setVisibility(View.VISIBLE);
                repost_progressBar.setVisibility(View.INVISIBLE);
                return;
            }
        });


    }
}