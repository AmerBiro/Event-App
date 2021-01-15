package dk.events.a6.mvvm.image_collections;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import dk.events.a6.mvvm.UserModel;


public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Query imageCollection = FirebaseFirestore.getInstance()
            .collection("user").document(userId)
            .collection("image collection").orderBy("number");


    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }



    public void getImageCollectionData() {
        imageCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                List<ImageCollectionModel> imageCollectionModels = value.toObjects(ImageCollectionModel.class);
                onFirestoreTaskComplete.imageCollectionDataAdded(imageCollectionModels);
            }
        });
    }




    public interface OnFirestoreTaskComplete {
        void imageCollectionDataAdded(List<ImageCollectionModel> imageCollectionModels);

        void onError(Exception e);
    }

}