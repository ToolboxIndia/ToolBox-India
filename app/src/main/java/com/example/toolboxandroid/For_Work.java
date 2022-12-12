package com.example.toolboxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.HashMap;

public class For_Work extends AppCompatActivity {

    Button btnApply;
    EditText numInput,addressIn;
    SharedPreferences sharedPreferences;
    String EmailId;
    private static final String SHARED_PREF_NAME="mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_work);

        btnApply = findViewById(R.id.btnApply);
        addressIn = findViewById(R.id.addressIn);
        numInput = findViewById(R.id.numInput);

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
                w.put("Experience",numInput.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Workers").push().setValue(w);
                Toast.makeText(For_Work.this, "Succes", Toast.LENGTH_SHORT).show();

            }
        });

    }
}