package com.example.licentafii2022;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class EditProfile extends AppCompatActivity {

    EditText name,bio,phone,email;
    Button button;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = findViewById(R.id.name_ep);
        bio = findViewById(R.id.bio_ep);
        phone = findViewById(R.id.phone_ep);
        email = findViewById(R.id.email_ep);
        button = findViewById(R.id.button_ep);
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();
        documentReference = db.collection("user").document(userId);
        button.setOnClickListener(view -> {
            String name = this.name.getText().toString();
            String bio = this.bio.getText().toString();
            String phone = this.phone.getText().toString();
            String email = this.email.getText().toString();
            final DocumentReference dr = db.collection("user").document(userId);
            db.runTransaction((Transaction.Function<Void>) transaction -> {
                transaction.update(dr, "name", name);
                transaction.update(dr, "phone", phone);
                transaction.update(dr, "email", email);
                transaction.update(dr, "bio", bio);
                return null;
            }).addOnSuccessListener(aVoid -> {Toast.makeText(EditProfile.this,"updated",Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(EditProfile.this,MainActivity.class);
                    startActivity(intent);
                },1000);
                })
                    .addOnFailureListener(e -> Toast.makeText(EditProfile.this,"failed",Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    protected void onStart() {
         super.onStart();
         documentReference.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                String name = task.getResult().getString("name");
                String bio = task.getResult().getString("bio");
                String phone = task.getResult().getString("phone");
                String email = task.getResult().getString("email");
                this.name.setText(name);
                this.phone.setText(phone);
                this.bio.setText(bio);
                this.email.setText(email);
            }

        });
    }

}