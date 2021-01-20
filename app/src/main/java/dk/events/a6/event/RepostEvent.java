package dk.events.a6.event;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
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

    private String image, name, address, date, time, type, description, distance;
    private String reposter_id, reposter_image, reposter_name, reposter_gender, reposter_age;
    private CollectionReference repostEventRef;
    private NavController controller;
    private View view;
    private Activity activity;
    private int cost, min, max;

    public RepostEvent(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.activity = activity;
        this.controller = Navigation.findNavController(this.view);
    }

    public void Repost(String image, String name, int cost, String address, String date, String time,
                       int min, int max, String type, String description, String distance,
                       String reposter_id, String reposter_image, String reposter_name,
                       String reposter_gender, String reposter_age,
                       ImageButton repostButton, ProgressBar repost_progressBar, int action
    ) {
        repostButton.setVisibility(View.INVISIBLE);
        repost_progressBar.setVisibility(View.VISIBLE);

        this.image = image;
        this.name = name;
        this.cost = cost;
        this.min = min;
        this.max = max;
        this.address = address;
        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
        this.distance = distance;
        this.reposter_id = reposter_id;
        this.reposter_image = reposter_image;
        this.reposter_name = reposter_name;
        this.reposter_gender = reposter_gender;
        this.reposter_age = reposter_age;

        Map<String, Object> repost = new HashMap<>();


        repost.put("image", this.image);
        repost.put("name", this.name);
        repost.put("cost", this.cost);
        repost.put("address", this.address);
        repost.put("date", this.date);
        repost.put("time", this.time);
        repost.put("min", this.min);
        repost.put("max", this.max);
        repost.put("type", this.type);
        repost.put("description", this.description);
        repost.put("distance", this.distance);
        repost.put("creator_id", this.reposter_id);
        repost.put("creator_image", this.reposter_image);
        repost.put("creator_name", this.reposter_name);
        repost.put("creator_gender", this.reposter_gender);
        repost.put("creator_age", this.reposter_age);

        this.repostEventRef = FirebaseFirestore.getInstance()
                .collection("event");
        this.repostEventRef.add(repost).addOnCompleteListener(task -> {
            // Successfully Reposting event
            if (task.isSuccessful()) {
                controller.navigate(action);
                Log.d(TAG, "Repost: " + "Reposted eventId: " + task.getResult().getId());
                repost_progressBar.setVisibility(View.INVISIBLE);
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