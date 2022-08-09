package com.example.licentafii2022;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartAnimationScreen extends AppCompatActivity {


    /**
     * Acesta va fi ecranul de inceput pentru (cel cu animatii)
     * Toti utilizatorii vor vedea acest ecran cand.
     * */
    ImageView carLogo, androidLogo;
    TextView myCarText, androidAppText;
    
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ObjectAnimator animatorCarLogo, animatorMyCarText, animatorAndroidAppText, animatorAndroidLogo;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_animation_screen);
        long time = 3500;
        carLogo = findViewById(R.id.animation_car_logo);
        androidLogo = findViewById(R.id.animation_android_logo);
        myCarText = findViewById(R.id.animation_text_masina_mea);
        androidAppText = findViewById(R.id.animation_text_aplicatie_android);
        /*
        * se creeaza selecteaza animatiile din ecran si se selecteaza se creeaza animatie
        * */

        animatorCarLogo = ObjectAnimator.ofFloat(carLogo,"y", 400f);
        animatorMyCarText = ObjectAnimator.ofFloat(myCarText,"x",250f);
        animatorAndroidAppText = ObjectAnimator.ofFloat(androidAppText,"x",300f);
        animatorAndroidLogo = ObjectAnimator.ofFloat(androidLogo,"x",350f,650f);

        animatorCarLogo.setDuration(time);
        animatorAndroidLogo.setDuration(time);
        animatorMyCarText.setDuration(time);
        animatorAndroidAppText.setDuration(time);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCarLogo,animatorMyCarText, animatorAndroidLogo, animatorAndroidAppText);
        animatorSet.start();

         /*dupa ce se termina animatia userul este redirectionat*/
        Handler goTo = new Handler();
        goTo.postDelayed(() -> {
            if(user != null){
                Intent intent = new Intent(StartAnimationScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(StartAnimationScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}