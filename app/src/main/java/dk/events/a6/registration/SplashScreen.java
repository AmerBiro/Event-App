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

import static dk.events.a6.activities.MainActivity.TAG;


public class SplashScreen extends Fragment {

    private FirebaseAuth firebaseAuth;
    private static final String userStatus = "";
    private NavController navController;
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
        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);
        binding.status.setText("Checking User Account...");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null){
                    binding.status.setText("No account founded ...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navController.navigate(R.id.action_splashScreen_to_registeration);
                        }
                    },1000);
                }else{
                    binding.status.setText("Logged in...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SplashScreenDirections.ActionSplashScreenToEventViewer action =
                                    SplashScreenDirections.actionSplashScreenToEventViewer();
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Log.d(TAG, "onSuccess: "+ " SplashScreen userId: " + userId);
                            action.setUserId(userId);
                            navController.navigate(action);
                        }
                    },1500);
                }
            }
        },1500);
    }
}