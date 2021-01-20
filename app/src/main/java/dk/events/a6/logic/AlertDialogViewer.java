package dk.events.a6.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AlertDialogViewer {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    private NavController controller;
    private View view;
    private Activity activity;


    public AlertDialogViewer(Activity activity, View view) {
        this.view = view;
        this.activity = activity;
        this.controller = controller;
        this.controller = Navigation.findNavController(view);
    }


    public void logInOrCreateAccount(int createAccount, int logIn, int destination) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            AlertDialog.Builder account = new AlertDialog.Builder(activity);
            account.setTitle("Account needed");
            account.setMessage("You can log in / create a new account")
                    .setPositiveButton("Create account", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            controller.navigate(createAccount);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Log in", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            controller.navigate(logIn);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = account.create();
            alert.show();
        } else {
            controller.navigate(destination);
        }
    }


    public void verifyAccount(int createAccount, int logIn, int destination) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            AlertDialog.Builder account = new AlertDialog.Builder(activity);
            account.setTitle("Account needed");
            account.setMessage("You can log in / create a new account")
                    .setPositiveButton("Create account", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            controller.navigate(createAccount);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Log in", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            controller.navigate(logIn);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = account.create();
            alert.show();
        } else if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            AlertDialog.Builder account = new AlertDialog.Builder(activity);
            account.setTitle("Verifying Account");
            account.setMessage("You need to verify your account to be able to create events\n Verify Now?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity, "A verification email has been sent to: \n" +
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "Once you've verified your email, you can create, edit, share and delete events", 1).show();
                                        }
                                    }, 1500);
                                    return;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "Email verification was not sent!" + e.getMessage(), 1).show();
                                }
                            });
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = account.create();
            alert.show();

        } else {
            controller.navigate(destination);
        }

    }


}
