package dk.events.a6.profileView.updateprofile;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;
import java.util.Map;
import dk.events.a6.R;
import dk.events.a6.databinding.ActivityUpdateProfileBackgroundBinding;
import dk.events.a6.profileView.MyAccount;
import dk.events.a6.signInView.SelectProfileImages;

public class Update_Profile_Background extends AppCompatActivity {
    private ActivityUpdateProfileBackgroundBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private StorageReference storageReference;
    private FirebaseUser user;

    private String First_Name;
    private String Last_Name;
    private String Birthdate;
    private String Gender;
    private String Email;
    private String Password;
    private String Address;
    private String Job;
    private String Education;
    private String Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile__background);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));


        binding = ActivityUpdateProfileBackgroundBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                First_Name = value.getString("First_Name");
                Last_Name = value.getString("Last_Name");
                Birthdate = value.getString("Birthdate");
                Gender = value.getString("Gender");
                Email = value.getString("Email");
                Password = value.getString("Password");
                Address = value.getString("Address");
                Job = value.getString("Job");
                Education = value.getString("Education");
                Description = value.getString("Description");

                if (
                        Address == null &&
                        Job == null &&
                        Education == null &&
                        Description == null
                ){
                    Address = ""; Job = ""; Education = ""; Description = "";
                    binding.Address.setText(Address);
                    binding.Job.setText(Job);
                    binding.Education.setText(Education);
                    binding.Description.setText(Description);

                } else{
                    binding.Address.setText(Address);
                    binding.Job.setText(Job);
                    binding.Education.setText(Education);
                    binding.Description.setText(Description);
                }
            }
        });


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                    binding.Address.getText().toString().isEmpty() &&
                    binding.Job.getText().toString().isEmpty() &&
                    binding.Education.getText().toString().isEmpty() &&
                    binding.Description.getText().toString().isEmpty()
                ){
                    Address = ""; Job = ""; Education = ""; Description = "";

                    user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference dataRef = fStore.collection("Users").document(userID);
                            Map<String, Object> updateUser = new HashMap<>();
                            updateUser.put("First_Name", First_Name);
                            updateUser.put("Last_Name", Last_Name);
                            updateUser.put("Gender",Gender);
                            updateUser.put("Birthdate", Birthdate);
                            updateUser.put("Email", Email);
                            updateUser.put("Password", Password);
                            updateUser.put("Address", Address);
                            updateUser.put("Job", Job);
                            updateUser.put("Education", Education);
                            updateUser.put("Description", Description);
                            dataRef.set(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(Update_Profile_Background.this, "Email is changed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(Update_Profile_Background.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Update_Profile_Background.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(Update_Profile_Background.this, "All fields are empty. You can update your background info in /Update Profile Background/", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MyAccount.class));
                    finish();
                    return;
//                    return;
                }
                else {
                    user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference dataRef = fStore.collection("Users").document(userID);
                            Map<String, Object> updateUser = new HashMap<>();
                            updateUser.put("First_Name", First_Name);
                            updateUser.put("Last_Name", Last_Name);
                            updateUser.put("Gender",Gender);
                            updateUser.put("Birthdate", Birthdate);
                            updateUser.put("Email", Email);
                            updateUser.put("Password", Password);
                            updateUser.put("Address", binding.Address.getText().toString());
                            updateUser.put("Job", binding.Job.getText().toString());
                            updateUser.put("Education", binding.Education.getText().toString());
                            updateUser.put("Description", binding.Description.getText().toString());
                            dataRef.set(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(Update_Profile_Background.this, "Email is changed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(Update_Profile_Background.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Update_Profile_Background.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), MyAccount.class));
                    finish();
                    return;
                }
            }
        });

    }
}