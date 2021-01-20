package dk.events.a6.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;


public class EventDetails extends Fragment implements View.OnClickListener {

    private @NonNull
    EventEventDetailsBinding binding;
    private NavController controller;
    private DocumentReference documentReference;
    private EventViewModel eventViewModel;
    private int position;
    private String eventId;
    private ShareEvent shareEvent;
    private GetEventData getEventData;
    private String reposter_id, reposter_image, reposter_name, reposter_gender, reposter_age;
    private UserModel userModel;


    //send request
    private DocumentReference mUsersDatabase;
    private String current_state;
    private String sender_user_id;
    private String receiver_user_id;
    private FirebaseAuth mAuth;
    private DatabaseReference chatRequestRef;
    private DatabaseReference contactsRef;
    private RepostEvent repostEvent;

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
        Log.d(TAG, "onSuccess: " + "Position: " + position);

        mAuth = FirebaseAuth.getInstance();
        sender_user_id = mAuth.getUid();
        current_state = "new";

        //define chat request
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Request");
        contactsRef = FirebaseDatabase.getInstance().getReference().child("contacts");

        repostEvent = new RepostEvent(controller, view, getActivity());

        reposter_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "onViewCreated: " + reposter_id);
        getUserData();

//        MangeChatRequests();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.eventDetailsShare.setOnClickListener(this);
        binding.eventDetailsImage.setOnClickListener(this);
        binding.eventDetailsBackArrow.setOnClickListener(this);
        binding.repost.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);
        eventViewModel.getEventModelData().observe(getViewLifecycleOwner(), new Observer<List<EventModel>>() {
            @Override
            public void onChanged(List<EventModel> eventModels) {
                Log.d(TAG, "onSuccess: " + "Event name: " + eventModels.get(position).getName());
                shareEvent = new ShareEvent(eventModels, getActivity());
                getEventData = new GetEventData(eventModels, position);
                eventId = getEventData.getEvent_id();

                Log.d(TAG, "onSuccess: " + "CreatorId: " + getEventData.getCreator_id());

                Picasso
                        .get()
                        .load(getEventData.getImage())
                        .fit()
                        .into(binding.eventDetailsImage);

                binding.eventDetailsName.setText(getEventData.getName());
                binding.eventDetailsCost.setText(getEventData.getCost() + " DKK");
                binding.eventDetailsLocation.setText(getEventData.getAddress());
                binding.eventDetailsDateTime.setText(getEventData.getDate() + ", " + getEventData.getTime());
                binding.eventDetailsAgeRange.setText(getEventData.getMin() + "-" + getEventData.getMax());
                binding.eventDetailsType.setText(getEventData.getType());
                binding.eventDetailsDescription.setText(getEventData.getDescription());

            }
        });
    }

    private void getUserData() {
        DocumentReference userData = FirebaseFirestore.getInstance()
                .collection("user").document(reposter_id);
        userData.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userModel = value.toObject(UserModel.class);
                reposter_image = userModel.getImage_url_account();
                reposter_name = userModel.getFirst_name();
                reposter_gender = userModel.getGender();
                reposter_age = userModel.getDate_of_birth();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_details_share:
                shareEvent.shareEvent(position);
                break;
            case R.id.event_details_image:
                controller.navigate(R.id.action_eventDetails_to_eventViewer);
                controller.navigateUp();
                controller.popBackStack();
                break;
            case R.id.event_details_back_arrow:
                controller.navigate(R.id.action_eventDetails_to_eventViewer);
                controller.navigateUp();
                controller.popBackStack();
                break;
            case R.id.repost:
                repostEvent.Repost(
                        getEventData.getImage(),
                        getEventData.getName(),
                        getEventData.getCost(),
                        getEventData.getAddress(),
                        getEventData.getDate(),
                        getEventData.getTime(),
                        getEventData.getMin(),
                        getEventData.getMax(),
                        getEventData.getType(),
                        getEventData.getDescription(),
                        getEventData.getDistance(),
                        reposter_id,
                        reposter_image,
                        reposter_name,
                        reposter_gender,
                        reposter_age,
                        binding.repost,
                        binding.repostProgressBar,
                        R.id.action_eventDetails_to_homeViewpager
                        );
                break;
            default:
        }
    }


    private void MangeChatRequests() {
        chatRequestRef.child(sender_user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(eventId)) {
                            String request_type = snapshot.child(eventId).child("request_type").getValue().toString();
                            if (request_type.equals("sent")) {
                                current_state = "request_sent";
//                                //todo: done
                                binding.wishToJoinButton.setImageResource(R.drawable.ic_baseline_close_24);
                            } else if (request_type.equals("received")) {
                                current_state = "request_received";
                                binding.wishToJoinButton.setImageResource(R.drawable.wish_to_join_icon);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        if (!sender_user_id.equals(receiver_user_id)) {

            binding.wishToJoinButton.setOnClickListener(v -> {

                //sent request
                if (current_state.equals("new")) {
                    sendJoinRequest();
                }
                //cancel request
                if (current_state.equals("request_sent")) {
                    cancelJoinRequest();
                }
//                    //accept request
//                    if (current_state.equals("request_received")){
//                        acceptJoinRequest();
//                    }
//                    //remove friends
//                    if (current_state.equals("friends")){
//                        removeSpecificContact();
//                    }
            });

        } else {
            binding.wishToJoinButton.setVisibility(View.INVISIBLE);
        }
    }

    private void removeSpecificContact() {
        contactsRef
                .child(sender_user_id)
                .child(receiver_user_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            contactsRef
                                    .child(receiver_user_id)
                                    .child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                binding.wishToJoinButton.setEnabled(true);
                                                current_state = "new";
                                                binding.wishToJoinButton.setImageResource(R.drawable.wish_to_join_icon);


                                            }

                                        }
                                    });
                        }
                    }
                });
    }

    private void acceptJoinRequest() {
        contactsRef
                .child(sender_user_id)
                .child(receiver_user_id)
                .child("contacts")
                .setValue("saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            contactsRef
                                    .child(receiver_user_id)
                                    .child(sender_user_id)
                                    .child("contacts")
                                    .setValue("saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                chatRequestRef
                                                        .child(sender_user_id)
                                                        .child(receiver_user_id)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    chatRequestRef
                                                                            .child(receiver_user_id)
                                                                            .child(sender_user_id)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    binding.wishToJoinButton.setEnabled(true);
                                                                                    current_state = "friends";
                                                                                    binding.wishToJoinButton.setImageResource(R.drawable.ic_baseline_close_24);
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void sendJoinRequest() {

        chatRequestRef
                .child(sender_user_id)
                .child(eventId)
                .child("request_type")
                .setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            chatRequestRef
                                    .child(eventId)
                                    .child(sender_user_id)
                                    .child("request_type")
                                    .setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //todo:
                                                binding.wishToJoinButton.setImageResource(R.drawable.ic_baseline_close_24);
                                                current_state = "request_sent";
                                                //todo
                                                //sendReqBtn.setText("Cancel chat request");
                                            }

                                        }
                                    });
                        }
                    }
                });

    }

    private void cancelJoinRequest() {

        chatRequestRef

                .child(sender_user_id)
                .child(eventId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            chatRequestRef

                                    .child(eventId)
                                    .child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //todo
                                                binding.wishToJoinButton.setImageResource(R.drawable.wish_to_join_icon);
                                                current_state = "new";
                                                // sendReqBtn.setText("send request");
                                            }

                                        }
                                    });
                        }
                    }
                });

    }

}