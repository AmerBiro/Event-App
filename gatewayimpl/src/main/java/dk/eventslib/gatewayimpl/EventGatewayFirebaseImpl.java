package dk.eventslib.gatewayimpl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.createevent.EventGateway;

public class EventGatewayFirebaseImpl implements EventGateway {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public List<Event> findAllEvents() {
        return null;
    }

    @Override
    public Event findEventByTitle(String title) {
        return null;
    }

    @Override
    public Event getEvent(String id) {
        return null;
    }

    @Override
    public Event createEvent(Event event) {
        Map<String, Object> eventCollection = new HashMap<>();
        eventCollection.put("id", event.getId());
        eventCollection.put("title", event.getTitle());
        eventCollection.put("description", event.getDescription());
        eventCollection.put("event_image_id", UUID.randomUUID().toString());
        eventCollection.put("event_creator_id", event.getOwner().getId());

        firebaseFirestore.collection("events")
                .add(eventCollection)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });

        return event;
    }

    @Override
    public void delete(Event event) {

    }
}
