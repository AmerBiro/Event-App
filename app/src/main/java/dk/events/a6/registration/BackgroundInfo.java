package dk.events.a6.registration;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;

import dk.events.a6.R;
import dk.events.a6.databinding.RegistrationBackgroundInfoBinding;
import dk.events.a6.logic.FieldChecker;
import dk.events.a6.user.UserAuth;
import dk.events.a6.user.UserDatebase;

import static android.content.ContentValues.TAG;


public class BackgroundInfo extends Fragment implements View.OnClickListener {

    private @NonNull
    RegistrationBackgroundInfoBinding
     binding;
    private NavController controller;
    private String userId;
    private FieldChecker checker;
    private UserAuth userAuth;
    private EditText[] fields;
    private String[] errorMessage;
    private UserDatebase userDatebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationBackgroundInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                Log.d("BACKBUTTON", "Back button clicks");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        checker = new FieldChecker(getActivity());
        userAuth = new UserAuth(getActivity(), view, controller);
        userDatebase = new UserDatebase(controller, view, getActivity());
        fields = new EditText[4];
        errorMessage = new String[4];
        fields[0] = binding.address;
        fields[1] = binding.education;
        fields[2] = binding.job;
        fields[3] = binding.description;
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        Log.d(TAG, "onSuccess: " + "UserId in BackgroundInfo : " + userId);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.arrowNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_next:
                uploadBackgroundInfoToFirebase();
                break;
            default:
        }
    }

    private void uploadBackgroundInfoToFirebase() {
        for (int i = 0; i<fields.length; i++){
            if (fields[i].getText().toString().trim().isEmpty()){
                fields[i].setText("");
            }
        }
        userDatebase.uploadingUserBackgroundInfoToFirebase(userId, R.id.action_backgroundInfo_to_accountImages
        , fields[0], fields[1], fields[2], fields[3]);
    }



}