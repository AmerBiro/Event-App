package dk.events.a6.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dk.events.a6.R;
import dk.events.a6.activities.HomeActivity;
import dk.events.a6.adapters.CustomPagerAdapter;
import dk.events.a6.android.usecases.createevent.CreateEventActivityView;
import dk.events.a6.databinding.AccountViewPager0Binding;
import dk.events.a6.databinding.ActivityHomeBinding;
import dk.events.a6.fragments.ChatFragment;
import dk.events.a6.fragments.FavoriteFragment;
import dk.events.a6.mvvm.image_collections.ImageCollectionViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static dk.events.a6.activities.MainActivity.TAG;


public class AccountViewPager0 extends Fragment {

    private @NonNull
    AccountViewPager0Binding
     binding;
    private NavController controller;
    private String userId;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AccountViewPagerAdapter adapter;
    private AccountViewPager1 accountViewPager1;
    private AccountViewPager2 accountViewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountViewPager0Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        accountViewPager1 = new AccountViewPager1();
        accountViewPager2 = new AccountViewPager2();
        tabLayout = binding.accountTabLayout;
        viewPager = binding.accountViewPager;
        tabLayout.setupWithViewPager(viewPager);
        viewpagerSetup(view);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void viewpagerSetup(View view){
        adapter = new AccountViewPagerAdapter(getChildFragmentManager(), 0);

        //ViewPagerAdapter
        adapter.addFragment(accountViewPager1, "Account Info");
        adapter.addFragment(accountViewPager2, "Background");
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.info);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_edit_24);

    }

}