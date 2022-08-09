package com.example.licentafii2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PostCarActivity extends AppCompatActivity {

    ImageView imageView;
    Button chooseImageButton , uploadButton;
    TextView carName, price, fuel, km, engine;
    ProgressBar progressBar;
    private Uri imageUri;
    UploadTask uploadTask;
    String name, phone, url, email;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db1, db2;
    MediaController mediaController;
    FirebaseUser user;
    String userId;
    Car car;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_car);

        mediaController = new MediaController(this);
        imageView = findViewById(R.id.post_image_car);
        chooseImageButton = findViewById(R.id.choose_image_post_car_button);
        uploadButton = findViewById(R.id.upload_car_button);
        carName = findViewById(R.id.post_car_name);
        price = findViewById(R.id.post_car_price);
        km = findViewById(R.id.post_car_km);
        fuel = findViewById(R.id.fuel_post_car);
        engine = findViewById(R.id.post_car_engine_size);
        progressBar = findViewById(R.id.post_car_pb);
        storageReference = FirebaseStorage.getInstance().getReference("User Cars");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();
        car = new Car();
        db1 = database.getReference("All Images").child(userId);
        db2 = database.getReference("All Posts");
        chooseImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,1);
        });

        uploadButton.setOnClickListener(view -> {
            //carName, price, fuel, km, engine;
            String carNamePost = carName.getText().toString();
            String pricePost = price.getText().toString();
            String fuelPost = fuel.getText().toString();
            String kmPost = km.getText().toString();
            String enginePost = engine.getText().toString();

            Calendar calendarDate = Calendar.getInstance();
            Calendar calendarTime = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("dd-MMMM-yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            final String saveDate = date.format(calendarDate.getTime());
            final String saveTime = time.format(calendarTime.getTime());
            String moment = saveDate + " " + saveTime;

            if(!TextUtils.isEmpty(carNamePost) && !TextUtils.isEmpty(pricePost) && !TextUtils.isEmpty(fuelPost) &&
                    !TextUtils.isEmpty(kmPost) && !TextUtils.isEmpty(enginePost) && imageUri!= null) {
                final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
                uploadTask = reference.putFile(imageUri);

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        car.setUserName(name);
                        car.setName(carNamePost);
                        car.setUrl(url);
                        car.setPostUri(downloadUri.toString());
                        car.setTime(moment);
                        car.setUserId(userId);
                        car.setPrice(pricePost);
                        car.setFuel(fuelPost);
                        car.setKm(kmPost);
                        car.setEngine(enginePost);
                        car.setPhone(phone);
                        car.setEmail(email);

                        String id = db1.push().getKey();
                        db1.child(id).setValue(car);

                        String id1 = db2.push().getKey();
                        db2.child(id1).setValue(car);

                        Toast.makeText(PostCarActivity.this, "Car Uploaded", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent intent = new Intent(PostCarActivity.this,MainActivity.class);
                            startActivity(intent);
                        },1000);

                    }
                });
            }else{
                Toast.makeText(this, "Fill all Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }
    }
    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("user").document(userId);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                name = task.getResult().getString("name");
                url = task.getResult().getString("url");
                phone = task.getResult().getString("phone");
                email = task.getResult().getString("email");

            }else {
                Toast.makeText(PostCarActivity.this,"Error at database",Toast.LENGTH_SHORT).show();
            }

        });
    }
}