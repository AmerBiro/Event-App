package dk.events.a6.event;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.logic.FieldChecker;
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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class EditEvent extends Fragment implements View.OnClickListener {

    private @NonNull
    dk.events.a6.databinding.EventEditEventBinding
            binding;
    private NavController controller;
    private String eventId;
    private DocumentReference documentReference;
    private EventTypeView eventTypeView;
    private EventModel eventModel;
    private UpdateEvent updateEvent;
    private Uri eventUri;
    private int years, months, days;
    private int hr, mt;
    private int min, max, cost;
    private int imagePosition;

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

        binding.ageRange.setEnabled(false);
        eventTypeView = new EventTypeView(getActivity(), binding.editEventTypeInput);
        binding.editEventTypeInput.setEnabled(false);
        binding.editEventDate.setEnabled(false);
        binding.editEventTime.setEnabled(false);


        Calendar calendar = Calendar.getInstance();
        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        days = calendar.get(Calendar.DATE);
        binding.editEventDateSelector.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view1, year, month, dayOfMonth) -> {
                months = month + 1;
                days = dayOfMonth;
                years = year;
                String date = days + "/" + months + "/" + years;
                binding.editEventDate.setText(date);
            }, years, months, days);
            datePickerDialog.show();
        });

        binding.editEventTimeSelector.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hr = hourOfDay;
                            mt = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, hr, mt);

                            binding.editEventTime.setText(hr + ":" + mt);
                        }
                    }, 12, 0, true
            );
            timePickerDialog.show();
        });


        eventId = EditEventArgs.fromBundle(getArguments()).getEventId();
        Log.d(TAG, "onSuccess: " + "EventId in EditEvent: " + eventId);
        updateEvent = new UpdateEvent(controller, view, getActivity());

        binding.editEventAgeRange.setMinValue(18);
        binding.editEventAgeRange.setMaxValue(99);

        binding.editEventAgeRange.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            min = minValue.intValue();
            max = maxValue.intValue();
            binding.ageRange.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue));
        });
        getEventData();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.editEventClick.setOnClickListener(this);
        binding.editEventImage.setOnClickListener(this);
        binding.editEventTypeSelector.setOnClickListener(this);
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
            binding.editEventAgeRange.setMinStartValue(eventModel.getMin()).setMaxStartValue(eventModel.getMax()).apply();
        });
    }

    private void updateMyEvent() {
        String image, name, address, date, time, type, description;

        name = binding.editEventName.getText().toString();
        address = binding.editEventAddress.getText().toString();
        date = binding.editEventDate.getText().toString();
        time = binding.editEventTime.getText().toString();
        type = binding.editEventTypeInput.getText().toString();
        description = binding.editEventDescription.getText().toString();

        if (binding.editEventCost.getText().toString().trim().isEmpty()) {
            cost = 0;
        } else
            cost = Integer.parseInt(binding.editEventCost.getText().toString());

        updateEvent.updateEvent(R.id.action_editEvent_to_homeViewpager, eventId, eventUri, name, cost, address, date, time, min, max, type, description, binding.editEventClick, binding.editEventProgressBar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_event_click:
                updateMyEvent();
                break;
            case R.id.edit_event_image:
                imagePosition = 1500;
                openGallery(imagePosition);
                break;
            case R.id.edit_event_type_selector:
                eventTypeView.showEventTypeDialog();
                break;
            default:
        }
    }

    public void openGallery(int requestCode) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagePosition && resultCode == Activity.RESULT_OK) {
            eventUri = data.getData();
            String eventUriToString = eventUri.toString();
            Picasso
                    .get()
                    .load(eventUriToString)
                    .fit()
                    .into(binding.editEventImage);
        }
    }


}