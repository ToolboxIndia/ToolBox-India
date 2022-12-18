package com.example.toolboxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class For_Work extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnApply;
    Spinner cat;
    EditText numInput,addressIn,city,description,payment;
    SharedPreferences sharedPreferences;
    String EmailId;
    private static final String SHARED_PREF_NAME="mypref";

    Spinner type;


    @SuppressLint("MissingInflatedId")
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

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}