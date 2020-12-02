package dk.events.a6.signInView.functions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dk.events.a6.profileView.ProfileSettingsActivity;
import dk.events.a6.signInView.Registeration;


public class User {
    private Activity activity;

    public User() {
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    public void createUser(Activity activity, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(activity, "Account created successfully", 0).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Account not created! " + e.getMessage(), 1).show();
            }
        });
    }




    public void signIn(){

    }

    public void existingUser(){

    }

    public void signOut(){

    }

    public void verifyUser(){

    }

    public void updateEmail(){

    }

    public void updatePassword(){

    }
    
    public void deleteUser(Activity activity, Class<Registeration> afterDelete){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete account")
                .setMessage("Are you sure you want to delete the following profile " + "userEmail" + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "The following profile: " + "userEmail" +  " has been deleted successfully", 0).show();
                                activity.startActivity(new Intent(activity, afterDelete));
                                activity.finish();
                                return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Unable to delete profile " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
