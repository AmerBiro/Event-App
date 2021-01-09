package dk.eventslib.gatewayimpl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.eventslib.entities.Event;
import dk.eventslib.entities.ImageDetails;
import dk.eventslib.usecases.CreateEventProcessObservable;
import dk.eventslib.usecases.CreateEventProcessObserver;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObservable;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObserver;

public class EventGatewayFirebaseImpl implements EventGateway, CreateEventProcessObservable, FindAllEventsProcessObservable {
    private static final String EVENTS_COLLECTION = "events";
    public static final String TITLE_EVENT = "title";
    public static final String DESCRIPTION_EVENT = "description";
    public static final String ID_EVENT = "id";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



    List<FindAllEventsProcessObserver> findAllEventsObservers = new ArrayList<>();
    @Override
    public void addFindAllEventsProcessObserver(FindAllEventsProcessObserver observer){
        findAllEventsObservers.add(observer);
    }

    @Override
    public void removeFindAllEventsProcessObserver(FindAllEventsProcessObserver observer){
        findAllEventsObservers.remove(observer);
    }
    @Override
    public void findAllEventsAsync() {
        executor.execute( ()->{
            findAllEventsObservers.stream().forEach(o-> o.starting());

            firebaseFirestore
                    .collection(EVENTS_COLLECTION)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    findAllEventsObservers.stream().forEach(o-> o.pending() );
                    if (task.isSuccessful()) {
                        List<Event> events = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final Map<String, Object> map = document.getData();
                            events.add(
                                    Event.newBuilder()
                                            .withId(String.valueOf(map.get(ID_EVENT)))
                                            .withTitle(String.valueOf(map.get(TITLE_EVENT)))
                                            .withDescription(String.valueOf(map.get(DESCRIPTION_EVENT)))
                                            .build()
                            );
                            //document.getId()  document.getData()
                        }
                        findAllEventsObservers.stream().forEach(o-> o.onSuccess(events) );
                    } else {
                        findAllEventsObservers.stream().forEach( o-> o.onFailure( task.getException() ) );
                        //task.getException()
                    }
                }
            });

        });
        executor.shutdown();


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
                .setContentType("image/jpeg")
                .setCustomMetadata("config_name",details.getConfigName())
                .setCustomMetadata("height", String.valueOf(details.getHeight()))
                .setCustomMetadata("width", String.valueOf(details.getWidth())).build();
        UploadTask uploadTask = storageReference.putBytes(details.getPixels(),storageMetadata);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                createEventObservers.stream().forEach(o-> o.pending() );

                if(task.isSuccessful())
                    putEvent(event, details.getId());
                else
                    putEvent(event);
            }
        });

    }

    private void putEvent(Event event, final String imageId){
        Map<String, Object> eventCollection = new HashMap<>();
        eventCollection.put(ID_EVENT, event.getId());
        eventCollection.put(TITLE_EVENT, event.getTitle());
        eventCollection.put(DESCRIPTION_EVENT, event.getDescription());
        eventCollection.put("event_image_id", imageId);
        eventCollection.put("event_creator_id", event.getOwner()==null?null:event.getOwner().getId());

        firebaseFirestore.collection(EVENTS_COLLECTION)
                .add(eventCollection)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        createEventObservers.stream().forEach(o-> o.onSuccess(event) );
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createEventObservers.stream().forEach(o-> o.onFailure(event) );
                    }
                });
    }

    @Override
    public Event createEvent(Event event) {
        executor.execute( ()->{
            createEventObservers.stream().forEach(o-> o.starting());

            if(event.getImages().size()>0){
                final String imageId = UUID.randomUUID().toString();
                event.getImages().get(0).setId(imageId);
                putImage(event);
            }else {
                putEvent(event);
            }

        });
        executor.shutdown();
        return event;
    }

    private void putEvent(Event event) {
        putEvent(event,"no_image");
    }

    @Override
    public void delete(Event event) {

    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    List<CreateEventProcessObserver> createEventObservers = new ArrayList<>();
    @Override
    public void addCreateEventProcessObserver(CreateEventProcessObserver observer){
        createEventObservers.add(observer);
    }

    @Override
    public void removeCreateEventProcessObserver(CreateEventProcessObserver observer){
        createEventObservers.remove(observer);
    }
}
