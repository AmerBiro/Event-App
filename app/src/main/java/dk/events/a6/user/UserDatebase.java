package dk.events.a6.user;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.registration.BackgroundInfoDirections;
import dk.events.a6.registration.SignUpDirections;

import static android.content.ContentValues.TAG;


public class UserDatebase {

    private NavController controller;
    private View view;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private DocumentReference documentReference;
    private String first_name, last_name, date_of_birth, email, gender;
    private String address, education, job, description;
    private Activity activity;

    public UserDatebase(NavController controller, View view, Activity activity) {
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(this.view);
        this.activity = activity;
    }

    public UserDatebase() {

    }

    public void uploadUserInfoToFirebase(String userId, int action,
                                         ProgressBar progressBar, EditText first_name, EditText last_name, EditText date_of_birth, EditText email, String gender) {
        this.documentReference = FirebaseFirestore.getInstance().collection("user").document(userId);
        Map<String, Object> user = new HashMap<>();

        this.first_name = first_name.getText().toString();
        this.last_name = last_name.getText().toString();
        this.date_of_birth = date_of_birth.getText().toString();
        this.email = email.getText().toString();
        this.gender = gender;

        user.put("first_name", this.first_name);
        user.put("last_name", this.last_name);
        user.put("date_of_birth", this.date_of_birth);
        user.put("email", this.email);
        user.put("gender", this.gender);


        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onSuccess: " + "Uploading user database successfully after creating a new user");
                controller.navigate(action);
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void uploadingUserBackgroundInfoToFirebase(String userId, int action, EditText address, EditText education, EditText job, EditText description) {

        this.documentReference = FirebaseFirestore.getInstance()
                .collection("user").document(userId);

        Map<String, Object> backgroundInfo = new HashMap<>();

        this.address = address.getText().toString();
        this.education = education.getText().toString();
        this.job = job.getText().toString();
        this.description = description.getText().toString();

        backgroundInfo.put("address", this.address);
        backgroundInfo.put("education", this.education);
        backgroundInfo.put("job", this.job);
        backgroundInfo.put("description", this.description);

        documentReference.update(backgroundInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + "Updating user background info successfully");
                controller.navigate(action);
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }


    public void updateUserBackgroundInfoToFirebase(String userId,
                                                   EditText address, EditText education,
                                                   EditText job, EditText description
                                                   ) {
        this.documentReference = FirebaseFirestore.getInstance()
                .collection("user").document(userId);

        Map<String, Object> backgroundInfo = new HashMap<>();

        this.address = address.getText().toString();
        this.education = education.getText().toString();
        this.job = job.getText().toString();
        this.description = description.getText().toString();

        backgroundInfo.put("address", this.address);
        backgroundInfo.put("education", this.education);
        backgroundInfo.put("job", this.job);
        backgroundInfo.put("description", this.description);

        documentReference.update(backgroundInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + "Updated successfully");
                Toast.makeText(activity, "Updated successfully", 0).show();
                return;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }




}
