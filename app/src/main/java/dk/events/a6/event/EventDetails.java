package dk.events.a6.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.databinding.EventEventDetailsBinding;
import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.model.UserModel;
import dk.events.a6.mvvm.viewmodel.EventViewModel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;


public class EventDetails extends Fragment implements View.OnClickListener {

    private @NonNull EventEventDetailsBinding binding;
    private NavController controller;
    private int position;
    private DocumentReference documentReference;
    private EventViewModel eventViewModel;
    private List<EventModel> currentEventModels;
    private UserModel userModel;
    private RepostEvent repostEvent;
    private String image_url, name, cost, address, date, time, age_range, type, description, distance;
    private String creator_name, creator_gender, creator_age;
    private String reposter_id, reposter_image, reposter_name, reposter_gender, reposter_age;
    private String imageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EventEventDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        position = EventDetailsArgs.fromBundle(getArguments()).getPosition();
        reposter_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        repostEvent = new RepostEvent(controller, view, getActivity());
        getReposterData();
        Log.d(TAG, "onSuccess: " + "Position: " + position);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.eventDetailsShare.setOnClickListener(this);
        binding.eventDetailsImage.setOnClickListener(this);
        binding.repost.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);
        eventViewModel.getEventModelData().observe(getViewLifecycleOwner(), new Observer<List<EventModel>>() {
            @Override
            public void onChanged(List<EventModel> eventModels) {
                Log.d(TAG, "onSuccess: " + "Event name: " +  eventModels.get(position).getName());
                currentEventModels = eventModels;

                image_url = eventModels.get(position).getImage();
                name = eventModels.get(position).getName();
                cost = eventModels.get(position).getCost();
                address = eventModels.get(position).getAddress();
                date = eventModels.get(position).getDate();
                time = eventModels.get(position).getTime();
                age_range = eventModels.get(position).getAge_range();
                type = eventModels.get(position).getType();
                description = eventModels.get(position).getDescription();
                distance = "";
                creator_name = eventModels.get(position).getCreator_name();
                creator_gender = eventModels.get(position).getCreator_gender();
                creator_age = eventModels.get(position).getCreator_age();
                imageId = eventModels.get(position).getEvent_id();

                Picasso
                        .get()
                        .load(image_url)
                        .fit()
                        .into(binding.eventDetailsImage);

                binding.eventDetailsName.setText(name);
                binding.eventDetailsCost.setText(cost);
                binding.eventDetailsLocation.setText(address);
                binding.eventDetailsDateTime.setText(date + ", " + time);
                binding.eventDetailsAgeRange.setText(age_range);
                binding.eventDetailsType.setText(type);
                binding.eventDetailsDescription.setText(description);
                binding.eventDetailsCreatorName.setText(creator_name);
                binding.eventDetailsCreatorSex.setText(creator_gender);
                binding.eventDetailsCreatorAge.setText(creator_age);

//                Log.d(TAG, "onSuccess: " + image_url);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_details_share:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, image_url + "\n\nBy:\n" +
                            "Creator Name: " + creator_name + "\n" +
                            "Creator Sex: " + creator_gender + "\n" +
                            "Creator Age: " + creator_age + "\n" + "\n" +
                            "Details\n" +
                            "Event's Name: " + name + "\n" +
                            "Event's Cost: " + cost + "\n" +
                            "Event's Location: " + address + "\n" +
                            "Event's Date: " + date + "\n" +
                            "Event's Time: " + time + "\n" +
                            "Event's Age Range: " + age_range + "\n" +
                            "Event's Type: " + type + "\n" +
                            "Event's Description: " + description);
                    intent.setType("text/plain");
                    getActivity().startActivity(intent);
                break;
            case R.id.event_details_image:
                EventDetailsDirections.ActionEventDetailsToEventViewer action =
                        EventDetailsDirections.actionEventDetailsToEventViewer();
                action.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
                break;
            case R.id.repost:
//                EventDetailsDirections.ActionEventDetailsToEventViewer action1 =
//                        EventDetailsDirections.actionEventDetailsToEventViewer();
//                action1.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                controller.navigate(action1);
//                controller.navigateUp();
//                controller.popBackStack();
                repostEvent.Repost(imageId,
                        name, cost, address, date, time,
                        age_range, type, description, distance,
                        reposter_id, reposter_image, reposter_name,
                        reposter_gender, reposter_age,
                        binding.repost, binding.repostProgressBar);
                break;
            default:
        }
    }


    private void getReposterData(){
        documentReference = FirebaseFirestore.getInstance()
                .collection("user").document(reposter_id);
        Log.d(TAG, "onSuccess: " + "ReposterId: " +  reposter_id);
        documentReference.addSnapshotListener((value, error) -> {
            userModel = value.toObject(UserModel.class);
            reposter_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            reposter_image = userModel.getImage_url_account();
            reposter_name = userModel.getFirst_name();
            reposter_gender = userModel.getGender();
            reposter_age = userModel.getDate_of_birth();
        });
    }



}