package dk.eventslib.gatewayimpl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.eventslib.entities.Entity;
import dk.eventslib.entities.Event;
import dk.eventslib.entities.ImageDetails;
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

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public void putImage(Event event) {
        final ImageDetails details = event.getImages().get(0);
        final String path = "event_images/" + details.getId() + ".jpg";
        StorageReference storageReference = firebaseStorage.getReference(path);
        StorageMetadata storageMetadata = new StorageMetadata.Builder()
                .setCustomMetadata("config_name",details.getConfigName())
                .setCustomMetadata("height", String.valueOf(details.getHeight()))
                .setCustomMetadata("width", String.valueOf(details.getWidth())).build();
        UploadTask uploadTask = storageReference.putBytes(details.getPixels(),storageMetadata);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                    putEvent(event, details.getId());
                else
                    putEvent(event);
            }
        });

    }

    private void putEvent(Event event, final String imageId){
        Map<String, Object> eventCollection = new HashMap<>();
        eventCollection.put("id", event.getId());
        eventCollection.put("title", event.getTitle());
        eventCollection.put("description", event.getDescription());
        eventCollection.put("event_image_id", imageId);
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
    }

    @Override
    public Event createEvent(Event event) {
        if(event.getImages().size()>0){
            final String imageId = UUID.randomUUID().toString();
            event.getImages().get(0).setId(imageId);
            putImage(event);
        }else {
            putEvent(event);
        }
        return event;
    }

    private void putEvent(Event event) {
        putEvent(event,"no_image");
    }

    @Override
    public void delete(Event event) {

    }
}
