package com.example.licentafii2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment screenScene = null;
            switch (item.getItemId()){
                case R.id.profile_bottom:
                    screenScene = new FragmentProfile();
                    break;
                case R.id.home_bottom:
                    screenScene = new FragmentHome();
                    break;
                case R.id.my_cars_bottom:
                    screenScene = new FragmentMyCars();
                    break;
                case R.id.search_bottom:
                    screenScene = new FragmentSearch();
                    break;
                case R.id.message_bottom:
                    screenScene = new FragmentMessages();
                    break;
            }
            assert screenScene != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, screenScene).commit();
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FragmentHome()).commit();
    }

}