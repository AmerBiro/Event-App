package dk.events.a6.account;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import dk.events.a6.R;
import dk.events.a6.databinding.AccountAccountBinding;
import dk.events.a6.mvvm.model.UserModel;
import dk.events.a6.user.UserAuth;

import static android.content.ContentValues.TAG;


public class Account extends Fragment implements View.OnClickListener {

    private @NonNull
    AccountAccountBinding
            binding;
    private NavController controller;
    private String userId, first_name, last_name, image;
    private DocumentReference reference;
    private UserAuth userAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AccountAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = AccountArgs.fromBundle(getArguments()).getUserId();
        userAuth = new UserAuth(getActivity(), view, controller);
        getUserData();
        Log.d(TAG, "onSuccess: " +  "Receiving userId successfully in Account: " + userId);
        if (FirebaseAuth.getInstance().getCurrentUser() != null &&
                !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            binding.notVerified.setVisibility(View.VISIBLE);
        }else binding.notVerified.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.overview.setOnClickListener(this);
        binding.accountImages.setOnClickListener(this);
        binding.accountInfo.setOnClickListener(this);
        binding.settings.setOnClickListener(this);
        binding.accountLogOut.setOnClickListener(this);
        binding.accountDeleteAccount.setOnClickListener(this);
    }

    public void getUserData(){
        reference = FirebaseFirestore.getInstance()
                .collection("user").document(userId);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                UserModel userModel = value.toObject(UserModel.class);
//                Log.d(TAG, "onEvent: " + userModel.getFirst_name());

                first_name = userModel.getFirst_name();
                last_name = userModel.getLast_name();
                image = userModel.getImage_url_account();
                binding.name.setText(first_name + " " + last_name);

                Picasso
                        .get()
                        .load(image)
                        .fit()
                        .into(binding.imageProfile);

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.overview:
                AccountDirections.ActionAccountToOverview action0 =
                        AccountDirections.actionAccountToOverview();
                action0.setUserId(userId);
                controller.navigate(action0);
                break;
            case R.id.account_images:
                AccountDirections.ActionAccountToImages action1 =
                        AccountDirections.actionAccountToImages();
                action1.setUserId(userId);
                controller.navigate(action1);
                break;
            case R.id.account_info:
                controller.navigate(R.id.action_account_to_accountViewPager0);
                break;
            case R.id.settings:
                AccountDirections.ActionAccountToSettings action2 =
                        AccountDirections.actionAccountToSettings();
                action2.setUserId(userId);
                controller.navigate(action2);
                break;
            case R.id.account_log_out:
                userAuth.signOut(R.id.action_account_to_registeration);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                },1500);
                break;
            case R.id.account_delete_account:
                userAuth.deleteUser(R.id.action_account_to_registeration);
                break;
            default:
        }
    }
}

