package dk.events.a6.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dk.events.a6.mvvm.FirebaseRepository;
import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.model.ImageCollectionModel;

public class EventViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<EventModel>> eventModelData = new MutableLiveData<>();

    public LiveData<List<EventModel>> getEventModelData() {
        return eventModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public EventViewModel() {
        firebaseRepository.getEventData();
    }

    @Override
    public void imageCollectionDataAdded(List<ImageCollectionModel> imageCollectionModels) {

    }

    @Override
    public void eventDataAdded(List<EventModel> eventModels) {
        eventModelData.setValue(eventModels);
    }

    @Override
    public void onError(Exception e) {

    }
}
