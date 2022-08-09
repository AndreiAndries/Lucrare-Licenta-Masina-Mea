package com.example.licentafii2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileImage extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    String userId, url;
    FirebaseUser user;
    DocumentReference reference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);

        imageView = findViewById(R.id.profile_image_pi);
        textView = findViewById(R.id.name_pi);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        reference = db.collection("user").document(userId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String name = task.getResult().getString("name");
                    url = task.getResult().getString("url");
                    Picasso.get().load(url).into(imageView);
                    textView.setText(name);
                }else
                    Toast.makeText(ProfileImage.this, "Profile doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}