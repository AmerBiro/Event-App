package dk.events.a6.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ImageCollectionViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<ImageCollectionModel>> imageCollectionModelData = new MutableLiveData<>();

    public LiveData<List<ImageCollectionModel>> getImageCollectionModelData() {
        return imageCollectionModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ImageCollectionViewModel() {
        firebaseRepository.getImageCollectionData();
    }


    @Override
    public void imageCollectionDataAdded(List<ImageCollectionModel> imageCollectionModels) {
        imageCollectionModelData.setValue(imageCollectionModels);
    }



    @Override
    public void onError(Exception e) {

    }
}
