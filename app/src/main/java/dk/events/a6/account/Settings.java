package dk.events.a6.account;


import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.auth.FirebaseAuth;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountSettingsBinding;
import user.UserAuth;

import static dk.events.a6.activities.MainActivity.TAG;

public class Settings extends Fragment implements View.OnClickListener {

    private @NonNull
    AccountSettingsBinding
     binding;
    private NavController controller;
    private String userId;
    private UserAuth userAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = AccountArgs.fromBundle(getArguments()).getUserId();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in Settings: " + userId);
        userAuth = new UserAuth(getActivity(), view, controller);
        if (FirebaseAuth.getInstance().getCurrentUser() != null &&
                !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            binding.verify.setVisibility(View.VISIBLE);
        }else binding.verify.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.settingsBackArrow.setOnClickListener(this);
        binding.verify.setOnClickListener(this);
        binding.updateEmail.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.resetPassword.setOnClickListener(this);
        binding.logOut.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_back_arrow:
                SettingsDirections.ActionSettingsToAccount action =
                        SettingsDirections.actionSettingsToAccount();
                action.setUserId(userId);
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
                break;
            case R.id.verify:

                break;
            case R.id.update_email:

                break;
            case R.id.change_password:

                break;
            case R.id.reset_password:
                userAuth.resetPassword();
                break;
            case R.id.log_out:
                userAuth.signOut(R.id.action_settings_to_registeration);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                },1500);
                break;
            case R.id.delete_account:
                userAuth.deleteUser(R.id.action_settings_to_registeration);
                break;
            default:
        }
    }
}