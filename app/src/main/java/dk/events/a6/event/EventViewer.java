package dk.events.a6.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.databinding.EventEventViewerBinding;
import dk.events.a6.mvvm.adapter.EventAdapter;
import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.viewmodel.EventViewModel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;


import java.util.List;

import static android.content.ContentValues.TAG;


public class EventViewer extends Fragment implements EventAdapter.OnEventItemClicked, View.OnClickListener {

    private @NonNull
    EventEventViewerBinding
     binding;
    private NavController controller;
    private EventViewModel eventViewModel;
    private ViewPager2 viewpager2;
    private EventAdapter adapter;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EventEventViewerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = EventViewerArgs.fromBundle(getArguments()).getUserId();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in EventViewer: " + userId);
        viewpager2Setup();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.homeHome.setOnClickListener(this);
        binding.homeAccount.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);
        eventViewModel.getEventModelData().observe(getViewLifecycleOwner(), new Observer<List<EventModel>>() {
            @Override
            public void onChanged(List<EventModel> eventModels) {

//                Log.d(TAG, "onChanged: " + eventModels.get(0).getEvent_id());
                adapter.setEventModels(eventModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void viewpager2Setup() {
        viewpager2 = binding.viewpager;
        adapter = new EventAdapter(this);
        viewpager2.setAdapter(adapter);
        adapter.setActivity(getActivity());
    }


    @Override
    public void onItemClicked(int position) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_home:
                controller.navigate(R.id.action_eventViewer_to_homeViewpager);
                break;
            case R.id.home_account:
                EventViewerDirections.ActionEventViewerToAccount action =
                        EventViewerDirections.actionEventViewerToAccount();
                action.setUserId(userId);
                controller.navigate(action);
            default:
        }
    }
}