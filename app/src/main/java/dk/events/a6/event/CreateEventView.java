package dk.events.a6.event;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;

import static android.content.ContentValues.TAG;

import dk.events.a6.databinding.EventCreateEventViewBinding;
import dk.events.a6.logic.DatePickerView;
import dk.events.a6.logic.FieldChecker;
import dk.events.a6.mvvm.model.UserModel;
import dk.events.a6.user.ImageHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class CreateEventView extends Fragment implements View.OnClickListener {

    private @NonNull
    EventCreateEventViewBinding
            binding;
    private NavController controller;
    private CreateEvent event;
    private DocumentReference userRef;
    private UserModel userModel;
    private EditText[] fields;
    private String[] data;
    private String userId;
    private int imagePosition;
    private ImageHandler imageHandler;
    private Uri eventUri;
    private FieldChecker checker;
    private String[] errorMessage;
    private EventTypeView eventTypeView;
    private int years, months, days;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EventCreateEventViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        event = new CreateEvent(controller, view, getActivity());
        fields = new EditText[8];
        data = new String[15];
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.eventProgressBar.setVisibility(View.INVISIBLE);
        checker = new FieldChecker(getActivity());
        errorMessage = new String[8];
        binding.ageRange.setEnabled(false);
        eventTypeView = new EventTypeView(getActivity(), binding.eventType);
        binding.eventType.setEnabled(false);
        binding.eventDate.setEnabled(false);
        Log.d(TAG, "onSuccess: " + "UserId in create event: " + userId);

        binding.eventAgeRange.setMinValue(18);
        binding.eventAgeRange.setMaxValue(99);

        binding.eventAgeRange.setOnRangeSeekbarChangeListener((minValue, maxValue) ->
                binding.ageRange.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue)));


        Calendar calendar = Calendar.getInstance();
       years = calendar.get(Calendar.YEAR);
       months = calendar.get(Calendar.MONTH);
       days = calendar.get(Calendar.DATE);
        binding.dateSelector.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view1, year, month, dayOfMonth) -> {
                        months = month+1;
                        days = dayOfMonth;
                        years = year;
                        String date = days + "/" + months + "/" + years;
                        binding.eventDate.setText(date);
                    }, years, months,days);
            datePickerDialog.show();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.eventClick.setOnClickListener(this);
        binding.eventImage.setOnClickListener(this);
        binding.selectEventType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_click:
                for (int i = 0; i < 8; i++) {
                    errorMessage[i] = "Field cannot be empty";
                }
                fields[0] = binding.eventName;
                fields[1] = binding.eventCost;
                fields[2] = binding.eventAddress;
                fields[3] = binding.eventDate;
                fields[4] = binding.eventTime;
                fields[5] = binding.ageRange;
                fields[6] = binding.eventDescription;
                fields[7] = binding.eventType;
                if (eventUri != null) {
                    if (!checker.isEmpty(fields, errorMessage)) {
                        createEvent();
                    }
                } else {
                    Toast.makeText(getContext(), "An image must be selected", 0).show();
                }


                break;
            case R.id.event_image:
                imagePosition = 1500;
                openGallery(imagePosition);
                break;
            case R.id.select_event_type:
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
                    .into(binding.eventImage);
        }
    }


    private void createEvent() {
        CreateEventViewDirections.ActionEventCreatorToEventViewer action =
                CreateEventViewDirections.actionEventCreatorToEventViewer();
        action.setUserId(userId);

        userRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId);

        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userModel = value.toObject(UserModel.class);
                String creator_image, creator_name, creator_age, creator_id, creator_gender;
                creator_image = userModel.getImage_url_account();
                creator_name = userModel.getFirst_name();
                creator_gender = userModel.getGender();
                creator_age = userModel.getDate_of_birth();

                event.createEvent(eventUri, fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                        binding.eventType.getText().toString(), fields[6], "", userId, creator_image,
                        creator_name, creator_gender, creator_age,
                        binding.eventClick, binding.eventProgressBar, action);
            }
        });

    }

}