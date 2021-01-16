package dk.events.a6.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.create_event.CreateEvent;
import dk.events.a6.databinding.EventEventCreatorBinding;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;


public class EventCreator extends Fragment {

    private @NonNull
    EventEventCreatorBinding
     binding;
    private NavController controller;
    private CreateEvent event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EventEventCreatorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        event = new CreateEvent(controller, view, getActivity());
        binding.eventAgeRange.setMinValue(18);
        binding.eventAgeRange.setMaxValue(99);

        binding.eventAgeRange.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                binding.ageRange.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }






}