package dk.events.a6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.activities.CreateActivity;
import dk.events.a6.adapters.ViewPagerAdapter;
import dk.events.a6.models.MyEventViewModel;
import dk.events.a6.models.TabViewModel;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private Button buttonCreateEvent;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, CreateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter( generateTabVMs(),this);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(position == 0 ? R.drawable.ic_baseline_chat_24 : R.drawable.ic_baseline_favorite_24);
                //tab.setText("TAB " + position);
            }
        });
        mediator.attach();
    }

    private List<TabViewModel> generateTabVMs() {
        List<TabViewModel> tabVMs = new ArrayList<>();
        for(int k=0;k<2;k++) {
            List<MyEventViewModel> events = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                events.add(MyEventViewModel.Builder.newInstance()
                        .withImageDrawableId(R.drawable.ic_launcher_background)
                        .withTitle("Title " + i)
                        .withDescription("Description " + i)
                        .build());
            }
            TabViewModel tabVM = new TabViewModel();
            tabVM.tabTitle = "Tab " + k + 1;
            tabVM.events = events;
            tabVMs.add(tabVM);
        }
        return tabVMs;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }


}