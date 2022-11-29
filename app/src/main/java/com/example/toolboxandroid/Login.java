package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText LoginEtEmail,LoginEtPassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        LoginEtEmail = (EditText) findViewById(R.id.LoginEtEmail);
        LoginEtPassword = (EditText) findViewById(R.id.LoginEtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Login Button
        authProfile = FirebaseAuth.getInstance();

        Button LoginBtn;

        LoginBtn = (Button) findViewById(R.id.LoginBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtEmail = LoginEtEmail.getText().toString();
                String txtPassword = LoginEtPassword.getText().toString();

                if(TextUtils.isEmpty(txtEmail)){
                    Toast.makeText(Login.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    LoginEtEmail.setError("Email Required");
                    LoginEtEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
                    Toast.makeText(Login.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    LoginEtEmail.setError("Valid Email Required");
                    LoginEtEmail.requestFocus();
                }else if(TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(Login.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    LoginEtPassword.setError("Password Required");
                    LoginEtPassword.requestFocus();
                }else{
                    loginUser(txtEmail,txtPassword);
                }

            }
        });

    }

    private void loginUser(String txtEmail, String txtPwd) {

        authProfile.signInWithEmailAndPassword(txtEmail,txtPwd).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "You Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent iHome = new Intent(Login.this,Home.class);
                    startActivity(iHome);
                    finish();


                }else{
                    Toast.makeText(Login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Intent iHome = new Intent(Login.this,Login.class);
                    startActivity(iHome);
                    finish();


                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
