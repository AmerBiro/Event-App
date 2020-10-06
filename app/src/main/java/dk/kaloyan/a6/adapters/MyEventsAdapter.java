package dk.kaloyan.a6.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.a6.R;
import dk.kaloyan.a6.models.MyEventViewModel;

public class MyEventsAdapter extends ArrayAdapter<MyEventViewModel> {
    private Context context;
    private List<MyEventViewModel> eventVMs = new ArrayList<>();

    public MyEventsAdapter(@NonNull Context context, List<MyEventViewModel> eventVMs) {
        super(context, 0, eventVMs);
        this.context = context;
        this.eventVMs = eventVMs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myEventView = convertView;
        if(myEventView == null)
            myEventView = LayoutInflater.from(this.context).inflate(R.layout.myevents_list_element, parent,false);

        MyEventViewModel myEventViewModel = eventVMs.get(position);

        ImageView imageView = (ImageView)myEventView.findViewById(R.id.imageView);
        imageView.setImageResource(myEventViewModel.imageDrawableId);

        TextView eventTitleView = (TextView) myEventView.findViewById(R.id.event_title);
        eventTitleView.setText(myEventViewModel.title);

        TextView eventDescriptionView = (TextView) myEventView.findViewById(R.id.event_description);
        eventDescriptionView.setText(myEventViewModel.description);

        return myEventView;
    }
}
