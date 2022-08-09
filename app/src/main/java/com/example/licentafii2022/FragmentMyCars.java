package com.example.licentafii2022;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FragmentMyCars extends Fragment implements View.OnClickListener {

    String userId;
    FirebaseUser user;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference,reference1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment5,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerView_f5);
        recyclerView.setHasFixedSize(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userId = user.getUid();
        reference = database.getReference("All Images").child(userId);
        reference1 = database.getReference("All Posts");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Car> optionsCar = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(reference, Car.class).build();

        FirebaseRecyclerAdapter<Car, ViewCar> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Car, ViewCar>(optionsCar) {
            @Override
            protected void onBindViewHolder(@NonNull ViewCar holder, int position, @NonNull Car model) {
                getRef(position).getKey();
                holder.setMyCarPost(getActivity(), model.getUserName(),model.getName(),model.getUrl(),model.getPostUri(),model.getTime(),model.getUserId(),
                        model.getPrice(),model.getFuel(),model.getKm(),model.getEngine(),model.getPhone(),model.getEmail());

                final String time = getItem(position).getTime();
                holder.delete.setOnClickListener(view -> {
                    Query timeQuery = reference.orderByChild("time").equalTo(time);
                    timeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    Query timeQuery1 = reference1.orderByChild("time").equalTo(time);
                    timeQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                });
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


    @Override
    public void onClick(View view) {

    }

}