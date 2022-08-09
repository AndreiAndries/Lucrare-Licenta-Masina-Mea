package com.example.licentafii2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentHome extends Fragment implements View.OnClickListener {


    String userId;
    FirebaseUser user;
    Button button;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.post_car_home_btn);
        recyclerView = getActivity().findViewById(R.id.recyclerView_f1);
        recyclerView.setHasFixedSize(true);
        reference = database.getReference("All Posts");
        button.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post_car_home_btn:
                startActivity(new Intent(getActivity(), PostCarActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Car> optionsCar = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(reference, Car.class).build();
        FirebaseRecyclerAdapter<Car, ViewCar> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Car, ViewCar>(optionsCar) {
            @Override
            protected void onBindViewHolder(@NonNull ViewCar holder, int position, @NonNull Car model) {
                final String postKey = getRef(position).getKey();
                holder.setCarPost(getActivity(), model.getUserName(),model.getName(),model.getUrl(),model.getPostUri(),model.getTime(),model.getUserId(),
                        model.getPrice(),model.getFuel(),model.getKm(),model.getEngine(),model.getPhone(),model.getEmail());
            }
            @NonNull
            @Override
            public ViewCar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_post_layout,parent,false);
                return new ViewCar(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
