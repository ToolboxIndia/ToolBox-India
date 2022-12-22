package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

    Button btnHire,btnWork;
    BottomNavigationView bottom_nav;
    DrawerLayout drawer;
    NavigationView navView;
    ImageView menu_img;
    ActionBarDrawerToggle toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);

        drawer = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);
        menu_img = findViewById(R.id.menu_img);


        //Drawer Navigation bar
        toolbar = new ActionBarDrawerToggle(HomePage.this,drawer,R.string.open, R.string.close);
        drawer.addDrawerListener(toolbar);
        toolbar.syncState();
        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });

        btnHire = findViewById(R.id.btnHire);
        btnWork = findViewById(R.id.btnWork);
        bottom_nav = findViewById(R.id.bottom_navigation);
        bottom_nav.setSelectedItemId(R.id.home);

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
                        return true;
                    case R.id.search:
                        Toast.makeText(HomePage.this, "Search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myacc:
                         startActivity(new Intent(getApplicationContext(),SetupActivity.class));
                         overridePendingTransition(0,0);
                         return true;

                }
                return false;
            }
        });
    }
}