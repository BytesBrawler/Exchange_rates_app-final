package com.project.yourExchangeRatesApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class WelcomeScreen extends AppCompatActivity {

    AppCompatButton btnSignIn;
    AppCompatButton btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        btnSignIn = (AppCompatButton)findViewById(R.id.btnsignUp);
        btnRegister = (AppCompatButton)findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignUp = new Intent(getApplicationContext(),signUp.class);
                startActivity(iSignUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(getApplicationContext(),login.class);
                startActivity(iLogin);
            }
        });
    }
}