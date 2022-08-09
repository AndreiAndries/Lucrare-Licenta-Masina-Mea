package com.example.licentafii2022;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateProfile extends AppCompatActivity {


    AllUsers user;
    ImageView profileImage;
    EditText name,phone,bio,email;
    Button saveButton;
    ProgressBar progressBar;
    Uri imageUri;
    String userId;
    StorageReference storageReference, reference;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseFirestore db;
    DocumentReference documentReference;
    UploadTask uploadTask;
    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        profileImage = findViewById(R.id.profile_image_cp);
        name = findViewById(R.id.name_cp);
        phone = findViewById(R.id.phone_cp);
        bio = findViewById(R.id.bio_cp);
        email = findViewById(R.id.email_cp);
        saveButton = findViewById(R.id.button_cp);
        progressBar = findViewById(R.id.progress_cp);
        assert userFirebase != null;
        userId = userFirebase.getUid();

        documentReference = db.collection("user").document(userId);
        storageReference = FirebaseStorage.getInstance().getReference("Profile images");

        databaseReference = database.getReference("All Users");


        saveButton.setOnClickListener(view -> uploadData());
        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(profileImage);
                Toast.makeText(this, "IMAGE CHANGED", Toast.LENGTH_SHORT).show();
            }
    }

    private void uploadData() {
        String uName = name.getText().toString();
        String uPhone = phone.getText().toString();
        String uMail = email.getText().toString();
        String uDescription = bio.getText().toString();
        if(!TextUtils.isEmpty(uName) && !TextUtils.isEmpty(uPhone) && !TextUtils.isEmpty(uMail) && !TextUtils.isEmpty(uDescription) && imageUri != null){
            progressBar.setVisibility(View.VISIBLE);
            reference = storageReference.child(System.currentTimeMillis() + "." + photoExtension(imageUri));
            uploadTask = reference.putFile(imageUri);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if(task.isSuccessful()){

                    Uri downloadUri = task.getResult();
                    user = new AllUsers(uName,userId,uPhone,uMail,uDescription);
                    user.setUri(downloadUri.toString());
                    Map<String,String> profile = new HashMap<>();
                    profile.put("name",uName);
                    profile.put("phone",uPhone);
                    profile.put("email",uMail);
                    profile.put("url",downloadUri.toString());
                    profile.put("bio",uDescription);
                    profile.put("UID",userId);
                    databaseReference.child(userId).setValue(user);
                    documentReference.set(profile).addOnSuccessListener(unused -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CreateProfile.this, "Profile Created!", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent intent = new Intent(CreateProfile.this,MainActivity.class);
                            startActivity(intent);
                        },1000);
                    });
                }
            });
        }else{
            Toast.makeText(this, "Fill all Fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private String photoExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }
}