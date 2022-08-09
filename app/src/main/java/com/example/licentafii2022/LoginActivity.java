package com.example.licentafii2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, passwd;
    Button registerButton, loginButton;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email);
        passwd = findViewById(R.id.login_passwd);
        registerButton = findViewById(R.id.login_to_signup);
        loginButton = findViewById(R.id.button_login);
        checkBox = findViewById(R.id.login_checkbox);
        progressBar = findViewById(R.id.progressbarr_login);
        firebaseAuth = FirebaseAuth.getInstance();
        passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            }else{
                passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            String mail = email.getText().toString();
            String pass = passwd.getText().toString();
            if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)){
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}