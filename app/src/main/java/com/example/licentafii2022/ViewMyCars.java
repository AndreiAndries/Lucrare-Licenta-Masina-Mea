package com.example.licentafii2022;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewMyCars extends RecyclerView.ViewHolder {
    public ViewMyCars(@NonNull View itemView) {
        super(itemView);
    }

    public void setMyCarPost(Application activity, String userName, String name, String url, String postUri, String time,
                             String userId, String price, String fuel, String km, String engine, String phone, String email){


        ImageView profilePicture = itemView.findViewById(R.id.my_profile_image_car_post_item);
        ImageView carPicture = itemView.findViewById(R.id.my_car_image);
        TextView userNameT = itemView.findViewById(R.id.my_car_post_username);
        TextView timeT = itemView.findViewById(R.id.my_car_post_date_time);
        TextView emailT = itemView.findViewById(R.id.my_post_car_email);
        TextView phoneT = itemView.findViewById(R.id.my_post_car_phone_number);
        TextView carNameT = itemView.findViewById(R.id.my_post_car_name_home);
        TextView engineT = itemView.findViewById(R.id.my_post_car_engine_home);
        TextView fuelT = itemView.findViewById(R.id.my_post_car_fuel_home);
        TextView kmsT = itemView.findViewById(R.id.my_post_car_km_home);
        TextView priceT = itemView.findViewById(R.id.my_post_car_price_home);
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

    private void deletePost() {
    }
}
