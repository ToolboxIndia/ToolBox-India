package com.example.toolboxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //hide action bar

        getSupportActionBar().hide();

        //open login activity
        Button firstLogin;

        firstLogin = (Button) findViewById(R.id.firstLogin);

        firstLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLogin;
                iLogin = new Intent(MainActivity.this,Login.class);
                startActivity(iLogin);
            }
        });






        //open Registration Activity

        Button firstRegister;

        firstRegister = (Button) findViewById(R.id.firstRegister);

        firstRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iregistration;
                iregistration = new Intent(MainActivity.this,Registration.class);
                startActivity(iregistration);
            }
        });

    }
}