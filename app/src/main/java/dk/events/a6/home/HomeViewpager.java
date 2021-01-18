package dk.events.a6.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.account.AccountViewPagerAdapter;
import dk.events.a6.databinding.HomeHomeViewpagerBinding;
import dk.events.a6.event.MyEvents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class HomeViewpager extends Fragment implements View.OnClickListener {

    private @NonNull
    HomeHomeViewpagerBinding
            binding;
    private NavController controller;
    private String userId;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AccountViewPagerAdapter adapter;
    private Chat chat;
    private MyEvents myEvents;
    private Favorite favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeHomeViewpagerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        chat = new Chat();
        myEvents = new MyEvents();
        favorite = new Favorite();
        tabLayout = binding.homeTabLayout;
        viewPager = binding.homeViewPager;
        tabLayout.setupWithViewPager(viewPager);
        viewpagerSetup(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.create.setOnClickListener(this);


    }


    private void viewpagerSetup(View view) {
        adapter = new AccountViewPagerAdapter(getChildFragmentManager(), 0);

        adapter.addFragment(myEvents, "My Events");
        adapter.addFragment(chat, "Chat");
        adapter.addFragment(favorite, "Favorite");
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_event_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_chat);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_favorite);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                controller.navigate(R.id.action_homeViewpager_to_eventCreator);
                break;
            default:
        }
    }
}