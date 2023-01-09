package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class For_Work extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //FusedLocationProviderClient geoLocation;
    Button btnApply,getLoc;
    Spinner cat;
    EditText numInput,addressIn,city,description,payment;
    SharedPreferences sharedPreferences;
    String EmailId;
    //private final static int REQUEST_CODE = 100;
    private static final String SHARED_PREF_NAME="mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_work);

        btnApply = findViewById(R.id.btnApply);
        addressIn = findViewById(R.id.addressIn);
        numInput = findViewById(R.id.numInput);
        city = findViewById(R.id._City);
        cat = findViewById(R.id.category);
        description = findViewById(R.id.description);
        payment = findViewById(R.id.Payment);

//        geoLocation = LocationServices.getFusedLocationProviderClient(this);
//
//        getLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLastLocatioin();
//            }
//        });

        Spinner spinner = findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String email = sharedPreferences.getString(SHARED_PREF_NAME,null);

        if(email != null){
            EmailId = email;
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String,Object> w = new HashMap<String,Object>();
                // Retrieving the value using its keys the file name
                // must be same in both saving and retrieving the data
                w.put("Email", EmailId);
                w.put("Address",addressIn.getText().toString());
                w.put("City", city.getText().toString());
                w.put("Category",cat.toString());
                w.put("Experience",numInput.getText().toString());
                w.put("Work Description",description.getText().toString());
                w.put("Work Payment",payment.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Workers").push().setValue(w);
                Toast.makeText(For_Work.this, "Succes", Toast.LENGTH_SHORT).show();

                Intent wProfile = new Intent(For_Work.this,work_profile.class);
                startActivity(wProfile);

            }
        });
    }

//    private void getLastLocatioin() {
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//
//            geoLocation.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if(location!=null){
//                                Geocoder geocoder = new Geocoder(For_Work.this, Locale.getDefault());
//                                try {
//
//                                    List<Address> addreses= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                                     addressIn.setText(addreses.get(0).getAddressLine(0));
//                                     city.setText(addreses.get(0).getLocality());
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                    });
//        }else {
//            askPermission();
//        }
//    }

//    private void askPermission() {
//        ActivityCompat.requestPermissions(For_Work.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (requestCode == REQUEST_CODE) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                getLastLocatioin();
//
//            } else {
//                Toast.makeText(this, "Please provide the require permission", Toast.LENGTH_SHORT).show();
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}