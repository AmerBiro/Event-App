package dk.events.a6.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountViewPager2Binding;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import static dk.events.a6.activities.MainActivity.TAG;


public class AccountViewPager2 extends Fragment {

    private @NonNull
    AccountViewPager2Binding
     binding;
    private NavController controller;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountViewPager2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in ViewPager2: " + userId);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}