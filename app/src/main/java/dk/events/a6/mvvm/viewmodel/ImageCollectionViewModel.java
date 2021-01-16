package dk.events.a6.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dk.events.a6.mvvm.FirebaseRepository;
import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.model.ImageCollectionModel;

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
    public void eventDataAdded(List<EventModel> eventModels) {

    }


    @Override
    public void onError(Exception e) {

    }
}
