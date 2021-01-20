package dk.events.a6.event;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class UpdateEvent {

    private String image, name, address, date, time, type, description;
    private DocumentReference updateEventRef;
    private NavController controller;
    private View view;
    private Activity activity;
    private int cost, min, max;

    public UpdateEvent(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.activity = activity;
        this.controller = Navigation.findNavController(this.view);
    }


    public void updateEvent(int action, String eventId, Uri uri, String name, int cost, String address, String date, String time,
                            int min, int max, String type, String description,
                            Button updateButton, ProgressBar updateEventProgressBar) {

        updateButton.setVisibility(View.INVISIBLE);
        updateEventProgressBar.setVisibility(View.VISIBLE);

        this.name = name;
        this.cost = cost;
        this.address = address;
        this.date = date;
        this.time = time;
        this.min = min;
        this.max = max;
        this.type = type;
        this.description = description;


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

        updateEventRef = FirebaseFirestore.getInstance()
                .collection("event").document(eventId);

        updateEventRef.update(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + "Updating event with eventId: " + eventId);

                StorageReference reference = FirebaseStorage.getInstance().getReference().child("event/" + eventId);

                if (uri != null){
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d(TAG, "onSuccess: " + "Success updating image");

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d(TAG, "onSuccess: " + "Success Downloading after updating image");

                                    String uriToString = uri.toString();

                                    DocumentReference reference1 = FirebaseFirestore.getInstance()
                                            .collection("event").document(eventId);

                                    Map<String, Object> eventImage = new HashMap<>();
                                    eventImage.put("image", uriToString);

                                    reference1.update(eventImage).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: " + "Success Updating event image database");

                                            updateEventProgressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(activity, "Updating successfully", 0).show();
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
                }else{
                    updateEventProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity, "Updating successfully", 0).show();
                    controller.navigate(action);
                    controller.navigateUp();
                    controller.popBackStack();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                updateButton.setVisibility(View.VISIBLE);
                updateEventProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "Error creating event", 0).show();
                Log.d(TAG, "onFailure: " + e.getStackTrace());
            }
        });

    }


}
