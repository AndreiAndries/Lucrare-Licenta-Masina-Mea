package com.example.licentafii2022;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewCar extends RecyclerView.ViewHolder {

    ImageView profilePicture, carPicture;
    TextView userNameT,timeT,emailT,phoneT,carNameT,engineT,fuelT,kmsT,priceT,delete;



    public ViewCar(@NonNull View itemView) {
        super(itemView);
    }

    //userName, name, url, postUri, time, userId, price, fuel, km, engine, phone, email;
    public void setCarPost(FragmentActivity activity,String userName, String name, String url, String postUri, String time,
    String userId, String price, String fuel, String km, String engine, String phone, String email){
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
        Picasso.get().load(url).into(profilePicture);
        Picasso.get().load(postUri).into(carPicture);
        userNameT.setText(userName);
        timeT.setText(time);
        emailT.setText(email);
        phoneT.setText(phone);
        carNameT.setText(name);
        engineT.setText(engine.concat("L"));
        fuelT.setText(fuel);
        kmsT.setText(km.concat("km"));
        priceT.setText(price.concat("€"));
    }


    public void setMyCarPost(FragmentActivity activity, String userName, String name, String url, String postUri, String time,
                             String userId, String price, String fuel, String km, String engine, String phone, String email){


        ImageView profilePicture = itemView.findViewById(R.id.profile_image_car_post_item);
        ImageView carPicture = itemView.findViewById(R.id.car_image);
        TextView userNameT = itemView.findViewById(R.id.car_post_username);
        TextView timeT = itemView.findViewById(R.id.car_post_date_time);
        TextView emailT = itemView.findViewById(R.id.post_car_email);
        TextView phoneT = itemView.findViewById(R.id.post_car_phone_number);
        TextView carNameT = itemView.findViewById(R.id.post_car_name_home);
        TextView engineT = itemView.findViewById(R.id.post_car_engine_home);
        TextView fuelT = itemView.findViewById(R.id.post_car_fuel_home);
        TextView kmsT = itemView.findViewById(R.id.post_car_km_home);
        TextView priceT = itemView.findViewById(R.id.post_car_price_home);
        delete = itemView.findViewById(R.id.delete_btn);
        delete.setVisibility(View.VISIBLE);
        Picasso.get().load(url).into(profilePicture);
        Picasso.get().load(postUri).into(carPicture);
        userNameT.setText(userName);
        timeT.setText(time);
        emailT.setText(email);
        phoneT.setText(phone);
        carNameT.setText(name);
        engineT.setText(engine.concat("L"));
        fuelT.setText(fuel);
        kmsT.setText(km.concat("km"));
        priceT.setText(price.concat("€"));
        
        
    }

    public void setSearchedCarPost(Application activity,String userName, String name, String url, String postUri, String time,
                           String userId, String price, String fuel, String km, String engine, String phone, String email){
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
        Picasso.get().load(url).into(profilePicture);
        Picasso.get().load(postUri).into(carPicture);
        userNameT.setText(userName);
        timeT.setText(time);
        emailT.setText(email);
        phoneT.setText(phone);
        carNameT.setText(name);
        engineT.setText(engine.concat("L"));
        fuelT.setText(fuel);
        kmsT.setText(km.concat("km"));
        priceT.setText(price.concat("€"));
    }
}
