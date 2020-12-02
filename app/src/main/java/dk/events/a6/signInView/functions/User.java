package dk.events.a6.signInView.functions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import dk.events.a6.profileView.MyAccount;
import dk.events.a6.signInView.Registeration;


public class User {
    public User() {
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();


    public void createUser(Activity activity, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
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


    public void signIn(Activity activity, ProgressBar progressBar, EditText email, EditText password) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Sign in successfully", 0).show();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Sign in error" + e.getMessage(), 1).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public Boolean signOut(Activity activity) {
        if (user != null) {
            mAuth.signOut();
            Toast.makeText(activity, "Signed out successfully", 0).show();
            return true;
        } else return false;
    }


    public void verifyUser(Activity activity) {
        if (user != null) {
            if (!user.isEmailVerified()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("You have not verified your email yet!");
                builder.setMessage("Do you want to verify your email?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(activity, "A verification email has been sent to: \n" + user.getEmail(), 0).show();
                                        mAuth.signOut();
                                        activity.startActivity(new Intent(activity.getApplicationContext(), Registeration.class));
                                        activity.finish();
                                        return;
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity, "Email verification was not sent!" + e.getMessage(), 1).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(activity, "You cannot see or edit your profile before verifying your email!", 1).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Intent intent = new Intent(activity, MyAccount.class);
                activity.startActivity(intent);
            }
        }
    }


    public void updateEmail(Activity activity, String newEmail) {
        user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity, "Email updated successfully1", 0).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Email not updated " + e.getMessage(), 1).show();
            }
        });
    }


    public void resetPassword(Activity activity, View v) {
        final EditText resetPassword = new EditText(v.getContext());
        resetPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(v.getContext());
        resetPasswordDialog.setTitle("Reset Password");
        resetPasswordDialog.setMessage("You can receive a link to reset your password by entering your email down below");
        resetPasswordDialog.setView(resetPassword);
        resetPasswordDialog.setPositiveButton("Send me a reset link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = resetPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(activity, "You have not entered your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "A reset password link is sent to " + email, 0).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "An error has been occured!\n" + e.getMessage(), 1).show();
                    }
                });
            }
        });
        resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        resetPasswordDialog.create().show();
    }


    public void deleteUser(Activity activity, Class<Registeration> afterDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete account")
                .setMessage("Are you sure you want to delete the following profile " + user.getEmail() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "The following profile: " + user.getEmail() + " has been deleted successfully", 0).show();
                                activity.startActivity(new Intent(activity, afterDelete));
                                activity.finish();
                                return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Unable to delete profile " + e.getMessage(), 1).show();
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
