package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText LoginEtEmail,LoginEtPassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "Login";
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME ="mypref";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        LoginEtEmail = (EditText) findViewById(R.id.LoginEtEmail);
        LoginEtPassword = (EditText) findViewById(R.id.LoginEtPassword);

        //Storing data into SharedPreferences(Local Storage)
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

//        progressBar = (ProgressBar) findViewById(R.id.progressbar);

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
                    Toast.makeText(Login.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    LoginEtEmail.setError("Valid Email Required");
                    LoginEtEmail.requestFocus();
                }else if(TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(Login.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    LoginEtPassword.setError("Password Required");
                    LoginEtPassword.requestFocus();
                }else{

                    // Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    // Storing the key and its value as the data fetched from edittext
                    myEdit.putString(SHARED_PREF_NAME,LoginEtEmail.getText().toString());
                    myEdit.apply();

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

                    //get instance of current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //check if email is verifird before

                    if(firebaseUser.isEmailVerified()){
                        Intent iHome = new Intent(Login.this,HomePage.class);
                        startActivity(iHome);
                        finish();
                    }else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAleartDialog();
                    }

                }else{

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        LoginEtEmail.setError("User Dose Not Exists or no longer valid.please register again");
                        LoginEtEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        LoginEtEmail.setError("Invalid credentials. kindly, check and re-enter");
                        LoginEtEmail.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Intent iHome = new Intent(Login.this,Login.class);
                    startActivity(iHome);
                    finish();

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAleartDialog() {
        //setup the alert Bulider
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
    //check user already login or not
    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged In!", Toast.LENGTH_SHORT).show();

            //start user profile activity
            startActivity(new Intent(Login.this,HomePage.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
        }
    }
}
