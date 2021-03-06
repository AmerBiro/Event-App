package dk.events.a6.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.events.a6.R;
import dk.events.a6.databinding.RegistrationSplashScreenBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


public class SplashScreen extends Fragment {

    private static final String userStatus = "";
    private NavController controller;
    private @NonNull
    RegistrationSplashScreenBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.status.setText("Checking User Account...");
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null){
                    binding.status.setText("No account founded ...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "onSuccess: "+ " SplashScreen no userId found ");
                            controller.navigate(R.id.action_splashScreen_to_registeration);
                        }
                    },1000);
                }else{
                    binding.status.setText("Logged in...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Log.d(TAG, "onSuccess: "+ " SplashScreen userId: " + userId);
                            controller.navigate(R.id.action_splashScreen_to_eventViewer);
                        }
                    },1500);
                }
            }
        },1500);
    }
}