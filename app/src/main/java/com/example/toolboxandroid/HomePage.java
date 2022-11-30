package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {


    Button btnHire,btnWork;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        btnHire = findViewById(R.id.btnHire);
        btnWork = findViewById(R.id.btnWork);
        bottom_nav = findViewById(R.id.bottom_navigation);

        btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hireIntent = new Intent(HomePage.this,For_Work.class);  //Hire page yet to be implement
                startActivity(hireIntent);
            }
        });

        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workIntent = new Intent(HomePage.this,For_Work.class);  //Work page yet to be implement
                startActivity(workIntent);
            }
        });
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(HomePage.this, "Home Page", Toast.LENGTH_SHORT).show();
                    case R.id.search:
                        Toast.makeText(HomePage.this, "Search", Toast.LENGTH_SHORT).show();
                    case R.id.myacc:
                        Toast.makeText(HomePage.this, "My Account", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
    }
}