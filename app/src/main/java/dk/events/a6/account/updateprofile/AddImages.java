package dk.events.a6.account.updateprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddImages<binding> {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String userID = mAuth.getCurrentUser().getUid();
    private ProgressDialog progressDialog;





    public void uploadeImageToFirebase(Uri imageUri, String imageName, Activity activity, ImageView imageView) {
        final StorageReference profilePicture = storageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"/" + imageName  +   ".jpg");
        profilePicture.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog = new ProgressDialog(activity);
                progressDialog.setTitle("Uploading image");
                progressDialog.setMessage("Please wait while we upload your image");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Toast.makeText(activity, "Image has been uploaded", Toast.LENGTH_SHORT).show();
                profilePicture.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressDialog.dismiss();
                        Map<String, Object> user = new HashMap<>();
                        user.put("UserImage", uri);
                        Glide.with(activity).load(uri).into(imageView);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(ViewProfilePicture.this, MyAccount.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        },4000);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(activity, "Failed uploading image!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
