package dk.events.a6.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.R;
import dk.events.a6.adapters.ChatAdapter;
import dk.events.a6.models.ChatList;


public class ChatFragment extends Fragment {
    private List<ChatList> chatLists= new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=  inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChatList();



        return view;
    }


    private void getChatList(){
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        chatLists.add(new ChatList("Kanotur ","Dig: så ses vi kl. 18","man",R.drawable.images));
        chatLists.add(new ChatList("Cykeltur ","Det er det vi gør!:)","fre",R.drawable.images2));
        recyclerView.setAdapter(new ChatAdapter(chatLists,getContext()));


    }
}