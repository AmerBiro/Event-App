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

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ViewPager viewPager;
    private MyEventsFragment myEventsFragment;
    private FavoriteEventsFragment favoriteEventsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //viewPager = findViewById(R.id.viewpager_container);
        List<String> events = new ArrayList<>();
        for(int i=0;i<30;i++)
            events.add("Event title " + i);
        String[] titleEvents = events.toArray(new String[events.size()]);
        ListView listView = findViewById(R.id.list_item);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new ArrayAdapter(this, R.layout.myevents_list_element, R.id.event_title, titleEvents));


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }


    private void setupViewPager(){
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(), 1);
        myEventsFragment = new MyEventsFragment();
        favoriteEventsFragment = new FavoriteEventsFragment();
        adapter.addFragment(myEventsFragment);
        adapter.addFragment(favoriteEventsFragment);
        viewPager.setAdapter(adapter);
        //TableLayout tableLayout = new TableLayout()
    }

}