package dk.events.a6.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;


public class EditEvent extends Fragment {

    private @NonNull
    dk.events.a6.databinding.EventEditEventBinding
     binding;
    private NavController controller;
    private int posiiton;

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
        posiiton = EditEventArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "onSuccess: " +  "Position in EditEvent: " + posiiton);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}