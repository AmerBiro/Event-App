package dk.kaloyan.a6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.a6.adapters.MyEventsAdapter;
import dk.kaloyan.a6.models.MyEventViewModel;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.my_event_list);
        listView.setOnItemClickListener(this);

        List<MyEventViewModel> events = new ArrayList<>();
        for(int i=0;i<30;i++) {
            events.add(MyEventViewModel.Builder.newInstance()
                    .withImageDrawableId(R.drawable.ic_launcher_background)
                    .withTitle("Title " + i)
                    .withDescription("Description " + i)
                    .build());
        }

        //listView.setAdapter(new ArrayAdapter(this, R.layout.myevents_list_element, R.id.event_title, titleEvents));
        listView.setAdapter(new MyEventsAdapter(this, events));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }



}