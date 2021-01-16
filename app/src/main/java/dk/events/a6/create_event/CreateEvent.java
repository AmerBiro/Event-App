package dk.events.a6.create_event;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static dk.events.a6.activities.MainActivity.TAG;

public class CreateEvent {

    private String name, cost, address, date, time, age_range, type, description, distance,
            creator_id, creator_image, creator_name, creator_gender, creator_age;
    private CollectionReference eventRef;
    private NavController controller;
    private View view;
    private Activity activity;

    public CreateEvent(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.activity = activity;

        controller = Navigation.findNavController(view);
    }

    public void createEvent(EditText name, EditText cost, EditText address, EditText date, EditText time,
                            EditText age_range, EditText type, EditText description, EditText distance,
                            EditText creator_id, EditText creator_image, EditText creator_name,
                            EditText creator_gender, EditText creator_age, int i){

        this.name = name.getText().toString();
        this.cost = cost.getText().toString();
        this.address = address.getText().toString();
        this.date = date.getText().toString();
        this.time = time.getText().toString();
        this.age_range = age_range.getText().toString();
        this.type = type.getText().toString();
        this.description = description.getText().toString();
        this.distance = distance.getText().toString();
        this.creator_id = creator_id.getText().toString();
        this.creator_image = creator_image.getText().toString();
        this.creator_name = creator_name.getText().toString();
        this.creator_gender = creator_gender.getText().toString();
        this.creator_age = creator_age.getText().toString();


        Map<String, Object> event = new HashMap<>();
        event.put("name"  , this.name);
        event.put("cost", this.cost);
        event.put("address", this.address);
        event.put("date", this.date);
        event.put("time", this.time);
        event.put("age_range", this.age_range);
        event.put("type", this.type);
        event.put("description" , this.description);
        event.put("distance", this.distance);
        event.put("creator_id", this.creator_id);
        event.put("creator_image", this.creator_image);
        event.put("creator_name", this.creator_name);
        event.put("creator_gender", this.creator_gender);

        eventRef = FirebaseFirestore.getInstance()
                .collection("event");

        this.eventRef.add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: " +  "Event created successfully: " + eventRef.getId());
                Toast.makeText(activity, name + " event created successfully", 0).show();
                controller.navigate(i);
                controller.navigateUp();
                controller.popBackStack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


}
