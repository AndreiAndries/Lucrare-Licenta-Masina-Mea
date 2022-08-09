package com.example.licentafii2022;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText email, passwd, confPasswd;
    Button registerButton, loginButton;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email_et);
        passwd = findViewById(R.id.register_passwd_et);
        confPasswd = findViewById(R.id.register_confpasswd_et);
        registerButton = findViewById(R.id.button_signin);
        loginButton = findViewById(R.id.signup_to_login);
        checkBox = findViewById(R.id.register_checkbox);
        progressBar = findViewById(R.id.progressbarr_register);
        firebaseAuth = FirebaseAuth.getInstance();
        passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                confPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                confPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        registerButton.setOnClickListener(view -> {

            String mail = email.getText().toString();
            String pass = passwd.getText().toString();
            String confPass = confPasswd.getText().toString();

            if(!TextUtils.isEmpty(mail) || !TextUtils.isEmpty(pass) ||!TextUtils.isEmpty(confPass)){
                if(pass.equals(confPass)){
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            sendToMain();
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(RegisterActivity.this,"Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this, "Passwords doesn't match!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void sendToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            sendToMain();
        }
    }
}