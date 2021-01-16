package dk.events.a6.mvvm;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.model.ImageCollectionModel;


public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private Query imageCollectionRef = FirebaseFirestore.getInstance()
            .collection("user").document(userId)
            .collection("image collection").orderBy("number");

    private CollectionReference eventRef = FirebaseFirestore.getInstance()
            .collection("event");


    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }



    public void getImageCollectionData() {
        imageCollectionRef.addSnapshotListener((value, error) -> {
//                Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
            List<ImageCollectionModel> imageCollectionModels = value.toObjects(ImageCollectionModel.class);
            onFirestoreTaskComplete.imageCollectionDataAdded(imageCollectionModels);
        });
    }

    public void getEventData(){
        eventRef.addSnapshotListener((value, error) -> {
           List<EventModel> eventModels = value.toObjects(EventModel.class);
           onFirestoreTaskComplete.eventDataAdded(eventModels);
        });
    }




    public interface OnFirestoreTaskComplete {
        void imageCollectionDataAdded(List<ImageCollectionModel> imageCollectionModels);
        void eventDataAdded(List<EventModel> eventModels);

        void onError(Exception e);
    }

}