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
import dk.events.a6.adapters.FavoriteEventAdapter;
import dk.events.a6.models.FavoriteEventList;


public class FavoriteFragment extends Fragment {

    private List<FavoriteEventList> favoriteEventLists= new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getEventList();
        return view;


    }
    private void getEventList(){
        favoriteEventLists.add(new FavoriteEventList("Kanotur ","Med Mads, 22 ",R.drawable.favorite_image1,R.drawable.images));
        favoriteEventLists.add(new FavoriteEventList("Cykeltur ","Med Alberthe 24 )",R.drawable.favorite_image2,R.drawable.images2));


        recyclerView.setAdapter(new FavoriteEventAdapter(favoriteEventLists,getContext()));


    }

}