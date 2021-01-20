package dk.events.a6.event;

import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import dk.events.a6.R;
import dk.events.a6.databinding.EventEventViewerBinding;
import dk.events.a6.logic.AlertDialogViewer;
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


import com.google.firebase.auth.FirebaseAuth;

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
    private int position;
    private AlertDialogViewer alertDialogViewer;
    private ShareEvent shareEvent;
    private GetEventData getEventData;
    private List<EventModel> getEventModels;

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
        alertDialogViewer = new AlertDialogViewer(getActivity(), view);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        Log.d(TAG, "onSuccess: " + "UserId in EventViewer: " + userId);
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
                Log.d(TAG, "onChanged: " + eventModels.get(0).getEvent_id());
                shareEvent = new ShareEvent(eventModels, getActivity());
                getEventModels = eventModels;

                Log.d(TAG, "onChanged: " + position);

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
        Log.d(TAG, "onItemClicked: " + "itemview" + position);
        EventViewerDirections.ActionEventViewerToEventDetails action =
                EventViewerDirections.actionEventViewerToEventDetails();
        action.setPosition(position);
        controller.navigate(action);
    }

    @Override
    public void onAvatarClicked(int position) {
        Log.d(TAG, "onItemClicked: " + "Avatar " + position);
        getEventData = new GetEventData(getEventModels, position);
        position = position;

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.d(TAG, "onAvatarClicked: " + "Event was created by another user");
            EventViewerDirections.ActionEventViewerToEventsCreator action =
                    EventViewerDirections.actionEventViewerToEventsCreator();
            action.setCreatorId(getEventData.getCreator_id());
            controller.navigate(action);
            return;
        } else if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(getEventData.getCreator_id())) {
            Log.d(TAG, "onAvatarClicked: " + "Event was created by the current user");
            controller.navigate(R.id.action_eventViewer_to_overview);
        } else {
            Log.d(TAG, "onAvatarClicked: " + "Event was created by another user");
            EventViewerDirections.ActionEventViewerToEventsCreator action =
                    EventViewerDirections.actionEventViewerToEventsCreator();
            action.setCreatorId(getEventData.getCreator_id());
            controller.navigate(action);
        }
    }

    @Override
    public void onShareButtonClicked(int position) {
        Log.d(TAG, "onItemClicked: " + "Share " + position);
        shareEvent.shareEvent(position);
    }

    @Override
    public void onFavoriteButtonClicked(int position) {
        Log.d(TAG, "onItemClicked: " + "Favorite " + position);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_home:
                alertDialogViewer.verifyAccount(R.id.action_eventViewer_to_signUp, R.id.action_eventViewer_to_registeration, R.id.action_eventViewer_to_homeViewpager);
                break;
            case R.id.home_account:
                alertDialogViewer.logInOrCreateAccount(R.id.action_eventViewer_to_signUp, R.id.action_eventViewer_to_registeration, R.id.action_eventViewer_to_account);
            default:
        }
    }
}