package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {


    private TextView textViewWelcome,textViewfullName,textViewEmail,textViewDob,textViewGender,textviewMobile;
    private ProgressBar progressBar;
    private String fullname,email,dob,gender,mobile;
    private ImageView imageView;
    private FirebaseAuth authProfile;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        getSupportActionBar().setTitle("Account SetUp");
        textViewWelcome = findViewById(R.id.textview_show_welcome);

        textViewfullName = findViewById(R.id.textview_show_fullname);
        textViewEmail = findViewById(R.id.textview_show_email);
        textViewDob = findViewById(R.id.textview_show_dob);
        textViewGender = findViewById(R.id.textview_show_gender);
        textviewMobile = findViewById(R.id.textview_show_mobile);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(this, "Something wents wrong! user details are not available at current moment",
                    Toast.LENGTH_LONG).show();


        }else{
            cheackIfEmailVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }





    }

    private void cheackIfEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }

    }

    private void showAlertDialog() {
        //setup the alert Bulider
        AlertDialog.Builder builder = new AlertDialog.Builder(SetupActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please Verify Your Email now. you can not login without email verification.");

        //open Email Apps if user click tap to continue
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //TO EMAIL APP IN NEW WINDOW AND NOT WITHIN OUR APP
                startActivity(intent);
            }
        });

        //create the aleart box
        AlertDialog alertDialog = builder.create();

        //show the aleart box
        alertDialog.show();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        //Extracting Users Refrance from database for
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    dob = readUserDetails.dob;
                    gender = readUserDetails.gender;
                    mobile = readUserDetails.mobile;

                    textViewWelcome.setText("Welcome" + fullname );
                    textViewfullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewDob.setText(dob);
                    textViewGender.setText(gender);
                    textviewMobile.setText(mobile);

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SetupActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    //Createing Action Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_refresh){
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }/*else if(id == R.id.menu_update_profile){
            Intent intent = new Intent(SetupActivity.this,UpdateProfileActivity.class);
            startActivity(intent);
        }else if(id == R.id.menu_email){
            Intent intent = new Intent(SetupActivity.this,UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id == R.id.menu_settings){
            Toast.makeText(this, "menu_settings", Toast.LENGTH_LONG).show();
        }else if(id == R.id.menu_change_password){
            Intent intent = new Intent(SetupActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
        }else if(id == R.id.menu_delete_profile){
            Intent intent =new Intent(SetupActivity.this,DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if(id == R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(this, "Logged Out ", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(SetupActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}