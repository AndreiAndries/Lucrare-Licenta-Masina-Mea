package com.example.licentafii2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewSearchedCars extends RecyclerView.Adapter<ViewSearchedCars.MyViewHolder> {

    Context context;
    ArrayList<Car> cars;

    public ViewSearchedCars(Context context, ArrayList<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.car_post_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Car car = cars.get(position);
        Picasso.get().load(car.getUrl()).into(holder.profilePicture);
        Picasso.get().load(car.getPostUri()).into(holder.carPicture);
        holder.userNameT.setText(car.getUserName());
        holder.timeT.setText(car.getTime());
        holder.emailT.setText(car.getEmail());
        holder.phoneT.setText(car.getPhone());
        holder.carNameT.setText(car.getName());
        holder.engineT.setText(car.getEngine().concat("L"));
        holder.fuelT.setText(car.getFuel());
        holder.kmsT.setText(car.getKm().concat("km"));
        holder.priceT.setText(car.getPrice().concat("€"));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView profilePicture, carPicture;
        TextView userNameT,timeT,emailT,phoneT,carNameT,engineT,fuelT,kmsT,priceT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.profile_image_car_post_item);
            carPicture = itemView.findViewById(R.id.car_image);
            userNameT = itemView.findViewById(R.id.car_post_username);
            timeT = itemView.findViewById(R.id.car_post_date_time);
            emailT = itemView.findViewById(R.id.post_car_email);
            phoneT = itemView.findViewById(R.id.post_car_phone_number);
            carNameT = itemView.findViewById(R.id.post_car_name_home);
            engineT = itemView.findViewById(R.id.post_car_engine_home);
            fuelT = itemView.findViewById(R.id.post_car_fuel_home);
            kmsT = itemView.findViewById(R.id.post_car_km_home);
            priceT = itemView.findViewById(R.id.post_car_price_home);
        }
    }
}
