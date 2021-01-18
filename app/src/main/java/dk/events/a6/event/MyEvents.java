package dk.events.a6.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.databinding.HomeMyEventsBinding;
import dk.events.a6.mvvm.adapter.MyEventAdapter;
import dk.events.a6.mvvm.model.EventModel;
import dk.events.a6.mvvm.viewmodel.EventViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;


public class MyEvents extends Fragment implements MyEventAdapter.OnEventItemClicked {

    private @NonNull
    HomeMyEventsBinding
            binding;
    private NavController controller;
    private RecyclerView recyclerView;
    private EventViewModel eventViewModel;
    List<EventModel> onSwipeEventModels;
    private MyEventAdapter adapter;
    private List<EventModel> eventModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeMyEventsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
        getEventData();
    }

    private void getEventData() {
        Query event = FirebaseFirestore.getInstance()
                .collection("event")
                .orderBy("creator_id")
                .startAt(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .endAt(FirebaseAuth.getInstance().getCurrentUser().getUid() + "\uf8ff");
        event.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                eventModel = value.toObjects(EventModel.class);
                onSwipeEventModels = eventModel;
                adapter.setEventModels(eventModel);
                adapter.notifyDataSetChanged();
                adapter.setActivity(getActivity());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        eventViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);
//        eventViewModel.getEventModelData().observe(getViewLifecycleOwner(), new Observer<List<EventModel>>() {
//            @Override
//            public void onChanged(List<EventModel> eventModels) {
//                onSwipeEventModels = eventModels;
//                adapter.setEventModels(eventModels);
//                adapter.notifyDataSetChanged();
//                adapter.setActivity(getActivity());
//            }
//        });
//    }


    @Override
    public void onItemClicked(int position) {

    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new MyEventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int onSwipePosition = viewHolder.getAdapterPosition();

                String image, names, address, date, time, age_range, type, description;
                String creator_name, creator_gender, creator_age;
                String distance;
                int costs;

                image = onSwipeEventModels.get(onSwipePosition).getImage();
                names = onSwipeEventModels.get(onSwipePosition).getName();
                costs = onSwipeEventModels.get(onSwipePosition).getCost();
                address = onSwipeEventModels.get(onSwipePosition).getAddress();
                date = onSwipeEventModels.get(onSwipePosition).getDate();
                time = onSwipeEventModels.get(onSwipePosition).getTime();
                age_range = onSwipeEventModels.get(onSwipePosition).getAge_range();
                type = onSwipeEventModels.get(onSwipePosition).getType();
                description = onSwipeEventModels.get(onSwipePosition).getDescription();

                creator_name = onSwipeEventModels.get(onSwipePosition).getCreator_name();
                creator_gender = onSwipeEventModels.get(onSwipePosition).getCreator_gender();
                creator_age = onSwipeEventModels.get(onSwipePosition).getCreator_age();

                if (direction == 4) {
                    Log.d(TAG, "onSuccess: " + "left");
                    DocumentReference documentReference = FirebaseFirestore.getInstance()
                            .collection("event")
                            .document(onSwipeEventModels.get(onSwipePosition).getEvent_id());

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete Event");
                    builder.setMessage("Are you sure that you want to delete the following event?\n"
                            + "Event name: " + names + "\n"
                            + "Date: " +  date + "\n"
                            + "Time: " + time)
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    documentReference.delete();
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    adapter.notifyDataSetChanged();
                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else if (direction == 8) {
                    Log.d(TAG, "onSuccess: " + "right");


                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, image + "\n\nBy:\n" +

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
                    adapter.notifyDataSetChanged();
                }
            }
        }).attachToRecyclerView(recyclerView);

    }

}