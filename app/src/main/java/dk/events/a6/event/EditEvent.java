package dk.events.a6.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.mvvm.model.EventModel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;


public class EditEvent extends Fragment {

    private @NonNull
    dk.events.a6.databinding.EventEditEventBinding
            binding;
    private NavController controller;
    private String eventId;
    private DocumentReference documentReference;
    private EventModel eventModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = dk.events.a6.databinding.EventEditEventBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        eventId = EditEventArgs.fromBundle(getArguments()).getEventId();
        Log.d(TAG, "onSuccess: " + "EventId in EditEvent: " + eventId);

        binding.editEventAgeRange.setMinValue(18);
        binding.editEventAgeRange.setMaxValue(99);


        binding.editEventAgeRange.setOnRangeSeekbarChangeListener((minValue, maxValue) ->
                binding.ageRange.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue)));
        getEventData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getEventData() {
        documentReference = FirebaseFirestore.getInstance()
                .collection("event").document(eventId);
        documentReference.addSnapshotListener((value, error) -> {
            eventModel = value.toObject(EventModel.class);
            Log.d(TAG, "onEvent: " + eventModel.getName());

            Picasso
                    .get()
                    .load(eventModel.getImage())
                    .fit()
                    .into(binding.editEventImage);


            binding.editEventName.setText(eventModel.getName());
            binding.editEventCost.setText(eventModel.getCost() + "");
            binding.editEventAddress.setText(eventModel.getAddress());
            binding.editEventDate.setText(eventModel.getDate());
            binding.editEventTime.setText(eventModel.getTime());
            binding.ageRange.setText(eventModel.getMin() + "-" + eventModel.getMax());
            binding.editEventTypeInput.setText(eventModel.getType());
            binding.editEventDescription.setText(eventModel.getDescription());

            binding.editEventAgeRange.setMinStartValue(18).setMaxStartValue(35).apply();


        });

    }


}