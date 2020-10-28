package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import dk.events.a6.fragments.ChatFragment;
import dk.events.a6.fragments.FavoriteFragment;
import dk.events.a6.R;
import dk.events.a6.adapters.CustomPagerAdapter;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private CustomPagerAdapter customPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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

        // Tabbed Activity
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_page);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());

        //ViewPagerAdapter
        customPagerAdapter.addFragment(new ChatFragment(), "ChatFragment");
        customPagerAdapter.addFragment(new FavoriteFragment(), "FavoriteFragment");
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_chat_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_favorite_border_24);
    }





    /*private List<TabViewModel> generateTabVMs() {
        List<TabViewModel> tabVMs = new ArrayList<>();
        for(int k=0;k<2;k++) {
            List<MyEventViewModel> events = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
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

     */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }


}