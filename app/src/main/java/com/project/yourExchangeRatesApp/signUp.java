package com.project.yourExchangeRatesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class signUp extends AppCompatActivity {
         Button signUp;
         Button login;
         Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.bSignUp);
        login = findViewById(R.id.blogin);
        skip = findViewById(R.id.bSkip);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilogin = new Intent(getApplicationContext(),login.class);
                startActivity(ilogin);
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iskip = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(iskip);
                finish();
            }
        });
    }
}