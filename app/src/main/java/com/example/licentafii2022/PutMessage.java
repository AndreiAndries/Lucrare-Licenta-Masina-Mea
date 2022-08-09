package com.example.licentafii2022;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PutMessage extends AppCompatActivity {

    EditText editText;
    Button sendButton;
    String userId, name, url;
    FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userMessages,messages;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_message);
        editText = findViewById(R.id.ask_pq);
        sendButton = findViewById(R.id.submit_question_pq);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();

        documentReference = db.collection("user").document(userId);
        messages = database.getReference("AllMessages");
        userMessages = database.getReference("User Messages").child(userId);
        message = new Message();

        sendButton.setOnClickListener(view -> {
            Calendar calendarDate = Calendar.getInstance();
            Calendar calendarTime = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("dd-MMMM-yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            final String saveDate = date.format(calendarDate.getTime());
            final String saveTime = time.format(calendarTime.getTime());
            String moment = saveDate + " " + saveTime;
            String mgs = editText.getText().toString();

            message.setName(name);
            message.setUrl(url);
            message.setUserId(userId);
            message.setMessage(mgs);
            message.setTime(moment);

            String id = userMessages.push().getKey();
            assert id != null;
            userMessages.child(id).setValue(message);

            String key = messages.push().getKey();
            message.setKey(id);
            assert key != null;
            messages.child(key).setValue(message);
            Toast.makeText(PutMessage.this, "Message Submitted!", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(PutMessage.this,MainActivity.class);
                startActivity(intent);
            },1000);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        documentReference.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                name = task.getResult().getString("name");
                url = task.getResult().getString("url");

            }else {
                Toast.makeText(PutMessage.this,"Error at database",Toast.LENGTH_SHORT).show();
            }

        });
    }
}