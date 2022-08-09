package com.example.licentafii2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserCars extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    String userId;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cars);

        recyclerView = findViewById(R.id.recyclerView_my_cars);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();

        reference = database.getReference("All Images").child(userId);

        FirebaseRecyclerOptions<Car> options = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(reference, Car.class).build();

        FirebaseRecyclerAdapter<Car, ViewMyCars> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Car, ViewMyCars>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewMyCars holder, int position, @NonNull Car model) {
                holder.setMyCarPost(getApplication(),model.getUserName(),model.getName(),model.getUrl(),model.getPostUri(),model.getTime(),model.getUserId(),
                        model.getPrice(),model.getFuel(),model.getKm(),model.getEngine(),model.getPhone(),model.getEmail());
            }

            @NonNull
            @Override
            public ViewMyCars onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_item,parent,false);
                return new ViewMyCars(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}