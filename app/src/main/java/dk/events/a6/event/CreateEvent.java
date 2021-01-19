package dk.events.a6.event;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;

public class CreateEvent {

    private String name, address, date, time, type, description, distance,
            creator_id, creator_image, creator_name, creator_gender, creator_age;
    private CollectionReference eventRef;
    private NavController controller;
    private View view;
    private Activity activity;
    private int cost, min, max;

    public CreateEvent(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.activity = activity;
        this.controller = Navigation.findNavController(this.view);
    }

    public void createEvent(Uri uri,
                            EditText name, int cost, EditText address, EditText date, EditText time,
                            int min, int max,  String type, EditText description, String distance,
                            String creator_id, String creator_image, String creator_name,
                            String creator_gender, String creator_age,
                            Button button, ProgressBar event_progressBar,
                            CreateEventViewDirections.ActionEventCreatorToEventViewer action) {
        button.setVisibility(View.INVISIBLE);
        event_progressBar.setVisibility(View.VISIBLE);

        this.name = name.getText().toString();
        this.cost = cost;
        this.address = address.getText().toString();
        this.date = date.getText().toString();
        this.time = time.getText().toString();
        this.min = min;
        this.max = max;
        this.type = type;
        this.description = description.getText().toString();
        this.distance = distance;
        this.creator_id = creator_id;
        this.creator_image = creator_image;
        this.creator_name = creator_name;
        this.creator_gender = creator_gender;
        this.creator_age = creator_age;

        if (this.cost == -1){
            this.cost = 0;
        }


        Map<String, Object> event = new HashMap<>();
        event.put("name", this.name);
        event.put("cost", this.cost);
        event.put("address", this.address);
        event.put("date", this.date);
        event.put("time", this.time);
        event.put("min", this.min);
        event.put("max", this.max);
        event.put("type", this.type);
        event.put("description", this.description);
        event.put("distance", this.distance);
        event.put("creator_id", this.creator_id);
        event.put("creator_image", this.creator_image);
        event.put("creator_name", this.creator_name);
        event.put("creator_gender", this.creator_gender);
        event.put("creator_age", this.creator_age);

        eventRef = FirebaseFirestore.getInstance()
                .collection("event");

        this.eventRef.add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: " + "A new eventId: " + documentReference.getId());

                String eventId = documentReference.getId();
                StorageReference reference = FirebaseStorage.getInstance().getReference().child("event/" + eventId);

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: " + "Success uploading image");

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d(TAG, "onSuccess: " + "Success Downloading image");

                                String uriToString = uri.toString();

                                DocumentReference reference1 = FirebaseFirestore.getInstance()
                                        .collection("event").document(eventId);

                                Map<String, Object> eventImage = new HashMap<>();
                                eventImage.put("image", uriToString);

                                reference1.update(eventImage).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: " + "Success Updating event database");

                                        event_progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(activity, "Event created successfully", 0).show();
                                        controller.navigate(action);
                                        controller.navigateUp();
                                        controller.popBackStack();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                button.setVisibility(View.VISIBLE);
                event_progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "Error creating event", 0).show();
                Log.d(TAG, "onFailure: " + e.getStackTrace());
            }
        });


    }


}
