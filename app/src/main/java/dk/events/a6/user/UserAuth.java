package dk.events.a6.user;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import dk.events.a6.account.Account;
import dk.events.a6.registration.RegistrationDirections;


public class UserAuth {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private View view;
    private NavController controller;
    private Activity activity;

    public UserAuth(Activity activity, View view, NavController controller) {
        this.view = view;
        this.controller = controller;
        this.controller = Navigation.findNavController(this.view);
        this.activity = activity;
    }

    public UserAuth() {
    }


    public void signIn(ProgressBar progressBar, EditText email, EditText password/*, int i*/) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        //                controller.navigate(i);
                        Toast.makeText(activity, "Sign in successfully", 0).show();
                        RegistrationDirections.ActionRegisterationToEventViewer action =
                                RegistrationDirections.actionRegisterationToEventViewer();
                        action.setUserId(authResult.getUser().getUid());
                        controller.navigate(action);
                    }
                }, 1000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(activity, "Sign in error " + e.getMessage(), 1).show();
                        return;
                    }
                }, 1000);
            }
        });
    }

    public Boolean signOut(int i) {
        if (user != null) {
            mAuth.signOut();
            Toast.makeText(activity, "Logged out successfully", 0).show();
            controller.navigate(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                }
            }, 1500);
            return true;
        } else return false;
    }


    public void resetPassword() {
        final EditText resetPassword = new EditText(activity);
        resetPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(activity);
        resetPasswordDialog.setTitle("Reset Password");
        resetPasswordDialog.setMessage("You can receive a link to reset your password by entering your email down below");
        resetPasswordDialog.setView(resetPassword);
        resetPasswordDialog.setPositiveButton("Send me a reset link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = resetPassword.getText().toString();
                if (email.trim().isEmpty()) {
                    Toast.makeText(activity, "You have not entered your email!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity, "A reset password link is sent to " + email, 0).show();
                            dialog.cancel();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity, "Error!\n" + e.getMessage(), 1).show();
                            return;
                        }
                    });
                }

            }
        });
        resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        resetPasswordDialog.create().show();
    }


    public void deleteUser(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete account")
                .setMessage("Are you sure you want to delete the following account " + user.getEmail() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "The following account: " + user.getEmail() + " has been deleted successfully", 0).show();
                                controller.navigate(i);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        activity.finish();
                                    }
                                }, 1500);
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


    public void verifyUser() {
        if (user != null) {
            if (!user.isEmailVerified()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("You have not verified your email yet!");
                builder.setMessage("Do you want to verify your email?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(activity, "A verification email has been sent to: \n" + user.getEmail(), 0).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(activity, "Once you've verified your email, you have to log in again", 0).show();
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
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Intent intent = new Intent(activity, Account.class);
                activity.startActivity(intent);
            }
        }
    }


    public void updateEmail(int i) {
        final EditText updatePassword = new EditText(activity);
        updatePassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        final AlertDialog.Builder updatePasswordDialog = new AlertDialog.Builder(activity);
        updatePasswordDialog.setTitle("Updating email");
        updatePasswordDialog.setMessage("Enter the new password");
        updatePasswordDialog.setView(updatePassword);
        updatePasswordDialog.setPositiveButton("Update Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String update = updatePassword.getText().toString();
                if (update.trim().isEmpty()) {
                    Toast.makeText(activity, "Invalid input!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    user.updateEmail(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity, "Email updated successfully", 0).show();
                            dialog.cancel();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mAuth.signOut();
                                    controller.navigate(i);
                                    controller.navigateUp();
                                    controller.popBackStack();
                                    controller.navigateUp();
                                    controller.popBackStack();
                                }
                            }, 1500);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity, "Error!\n" + e.getMessage(), 1).show();
                            return;
                        }
                    });
                }

            }
        });
        updatePasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        updatePasswordDialog.create().show();
    }


    public void updatePassword(int i) {
        final EditText updatePassword = new EditText(activity);
        updatePassword.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        final AlertDialog.Builder updatePasswordDialog = new AlertDialog.Builder(activity);
        updatePasswordDialog.setTitle("Update Password");
        updatePasswordDialog.setMessage("Enter your new password");
        updatePasswordDialog.setView(updatePassword);
        updatePasswordDialog.setPositiveButton("Update Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String update = updatePassword.getText().toString();
                if (update.trim().isEmpty()) {
                    Toast.makeText(activity, "Invalid input!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    user.updatePassword(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity, "Password updated successfully", 0).show();
                            dialog.cancel();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mAuth.signOut();
                                    controller.navigate(i);
                                    controller.navigateUp();
                                    controller.popBackStack();
                                    controller.navigateUp();
                                    controller.popBackStack();
                                }
                            }, 1500);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity, "Error!\n" + e.getMessage(), 1).show();
                            return;
                        }
                    });
                }

            }
        });
        updatePasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        updatePasswordDialog.create().show();
    }


}
