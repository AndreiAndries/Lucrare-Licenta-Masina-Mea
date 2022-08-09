package com.example.licentafii2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchedCarsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    String userId;
    FirebaseUser user;
    ViewSearchedCars searchedCars;
    ArrayList<Car> carList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_cars);

        Intent intent = getIntent();

        String carName = intent.getStringExtra(FragmentSearch.EXTRA_CAR_NAME);
        String maxEngine = intent.getStringExtra(FragmentSearch.EXTRA_MAX_ENGINE);
        String minPrice = intent.getStringExtra(FragmentSearch.EXTRA_MIN_PRICE);
        String minEngine = intent.getStringExtra(FragmentSearch.EXTRA_MIN_ENGINE);
        String maxPrice = intent.getStringExtra(FragmentSearch.EXTRA_MAX_PRICE);
        String minKm = intent.getStringExtra(FragmentSearch.EXTRA_MIN_KM);
        String maxKM = intent.getStringExtra(FragmentSearch.EXTRA_MAX_KM);
        String fuel = intent.getStringExtra(FragmentSearch.EXTRA_FUEL);


        recyclerView = findViewById(R.id.recyclerView_scars);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        carList = new ArrayList<>();
        reference = database.getReference("All Posts");
        searchedCars = new ViewSearchedCars(this,carList);
        recyclerView.setAdapter(searchedCars);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    boolean goodForShow = true;
                    Car car = dataSnapshot.getValue(Car.class);
                    if(!carName.equals("")) {
                        if (!car.getName().contains(carName))
                            goodForShow = false;
                    }
                    if(!fuel.equals("")) {
                        if (!car.getFuel().equals(fuel)) {
                            goodForShow = false;
                        }
                    }//(maxEngine)
                    if(!minKm.equals("")){
                        if (!maxKM.equals("")){
                            if(Integer.parseInt(car.getKm()) > Integer.parseInt(maxKM) || Integer.parseInt(car.getKm()) < Integer.parseInt(minKm)){
                                goodForShow = false;
                            }
                        }else{
                            if(Integer.parseInt(car.getKm()) < Integer.parseInt(minKm)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if(!maxKM.equals("")){
                        if (!minKm.equals("")){
                            if(Integer.parseInt(car.getKm()) > Integer.parseInt(maxKM) || Integer.parseInt(car.getKm()) < Integer.parseInt(minKm)){
                                goodForShow = false;
                            }
                        }else{
                            if(Integer.parseInt(car.getKm()) > Integer.parseInt(maxKM)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if(!minPrice.equals("")){
                        if (!maxPrice.equals("")){
                            if(Integer.parseInt(car.getPrice()) > Integer.parseInt(maxPrice) || Integer.parseInt(car.getPrice()) < Integer.parseInt(minPrice)){
                                goodForShow = false;
                            }
                        }else{
                            if(Integer.parseInt(car.getPrice()) < Integer.parseInt(minPrice)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if(!maxPrice.equals("")){
                        if (!minPrice.equals("")){
                            if(Integer.parseInt(car.getPrice()) > Integer.parseInt(maxPrice) || Integer.parseInt(car.getPrice()) < Integer.parseInt(minPrice)){
                                goodForShow = false;
                            }
                        }else{
                            if(Integer.parseInt(car.getPrice()) > Integer.parseInt(maxPrice)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if(!minEngine.equals("")){
                        if (!maxEngine.equals("")){
                            if(Float.parseFloat(car.getEngine()) > Float.parseFloat(maxEngine) || Float.parseFloat(car.getEngine()) < Float.parseFloat(minEngine)){
                                goodForShow = false;
                            }
                        }else{
                            if(Float.parseFloat(car.getEngine()) < Float.parseFloat(minEngine)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if(!maxEngine.equals("")){
                        if (!minEngine.equals("")){
                            if(Float.parseFloat(car.getEngine()) > Float.parseFloat(maxEngine) || Float.parseFloat(car.getEngine()) < Float.parseFloat(minEngine)){
                                goodForShow = false;
                            }
                        }else{
                            if(Float.parseFloat(car.getEngine()) > Float.parseFloat(maxEngine)) {
                                goodForShow = false;
                            }
                        }
                    }
                    if (goodForShow) {
                        carList.add(car);
                    }
                }
                searchedCars.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}