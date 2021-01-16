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
import dk.events.a6.mvvm.viewmodel.EventViewModel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;


public class EventDetails extends Fragment implements View.OnClickListener {

    private @NonNull
    EventEventDetailsBinding
     binding;
    private NavController controller;
    private int position;
    private EventViewModel eventViewModel;
    private List<EventModel> eventModels2;

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
        Log.d(TAG, "onSuccess: " + position);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.eventDetailsShare.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);
        eventViewModel.getEventModelData().observe(getViewLifecycleOwner(), new Observer<List<EventModel>>() {
            @Override
            public void onChanged(List<EventModel> eventModels) {
                Log.d(TAG, "onSuccess: " + eventModels.get(position).getName());

                eventModels2 = eventModels;

                Picasso
                        .get()
                        .load(eventModels.get(position).getImage())
                        .fit()
                        .into(binding.eventDetailsImage);

                binding.eventDetailsName.setText(eventModels.get(position).getName());
                binding.eventDetailsCost.setText(eventModels.get(position).getCost());
                binding.eventDetailsLocation.setText(eventModels.get(position).getAddress());
                binding.eventDetailsDateTime.setText(eventModels.get(position).getDate() + ", " + eventModels.get(position).getTime());
                binding.eventDetailsAgeRange.setText(eventModels.get(position).getAge_range());
                binding.eventDetailsType.setText(eventModels.get(position).getType());
                binding.eventDetailsDescription.setText(eventModels.get(position).getDescription());
                binding.eventDetailsCreatorName.setText(eventModels.get(position).getCreator_name());
                binding.eventDetailsCreatorSex.setText(eventModels.get(position).getCreator_gender());
                binding.eventDetailsCreatorAge.setText(eventModels.get(position).getCreator_age());

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_details_share:
                    String image, names, costs, address, date, time, age_range, type, description;
                    String creator_image, creator_name, creator_gender, creator_age;
                    String distance;

                    image = eventModels2.get(position).getImage();
                    names = eventModels2.get(position).getName();
                    costs = eventModels2.get(position).getCost();
                    address = eventModels2.get(position).getAddress();
                    date = eventModels2.get(position).getDate();
                    time = eventModels2.get(position).getTime();
                    age_range = eventModels2.get(position).getAge_range();
                    type = eventModels2.get(position).getType();
                    description = eventModels2.get(position).getDescription();

                    creator_image = eventModels2.get(position).getCreator_image();
                    creator_name = eventModels2.get(position).getCreator_name();
                    creator_gender = eventModels2.get(position).getCreator_gender();
                    creator_age = eventModels2.get(position).getCreator_age();

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, image + "\n\nBy:\n" +

//                    "Creator Image: " + creator_image + "\n" +
                            "Creator Name: " + creator_name + "\n" +
                            "Creator Sex: " + creator_gender + "\n" +
                            "Creator Age: " + creator_age + "\n" + "\n" +

                            "Details\n" +

                            "Event's Name: " + names + "\n" +
                            "Event's Cost: " + costs + "\n" +
                            "Event's Location: " + address + "\n" +
                            "Event's Date: " + date + "\n" +
                            "Event's Time: " + time + "\n" +
                            "Event's Age Range: " + age_range + "\n" +
                            "Event's Type: " + type + "\n" +
                            "Event's Description: " + description);
                    intent.setType("text/plain");
                    getActivity().startActivity(intent);

                break;
            default:
        }
    }
}