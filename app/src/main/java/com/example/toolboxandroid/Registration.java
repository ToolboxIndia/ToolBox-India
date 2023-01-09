package com.example.toolboxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {


    private EditText RegisterEtFullname,addhaar,RegisterEtEmail,RegisterEtBirthdate,RegisterEtMobile,RegisterEtPassword,RegisterEtConfirmPassword;
    private ProgressBar progressBar;
    private RadioGroup RegisterRadiogroup;
    private RadioButton RegisterRadioButtonSelected;
    private DatePickerDialog picker;
    private static final String TAG="Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Register");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        Toast.makeText(this, "You Can Now Register", Toast.LENGTH_LONG).show();

        RegisterEtFullname = (EditText) findViewById(R.id.RegisterEtFullname);
        RegisterEtEmail = (EditText) findViewById(R.id.RegisterEtEmail);
        addhaar = findViewById(R.id.addharData);
        RegisterEtBirthdate = (EditText) findViewById(R.id.RegisterEtBirthdate);
        RegisterEtMobile = (EditText) findViewById(R.id.RegisterEtMobile);
        RegisterEtPassword = (EditText) findViewById(R.id.RegisterEtPassword);
        RegisterEtConfirmPassword = (EditText) findViewById(R.id.RegisterEtConfirmPassword);

        RegisterRadiogroup = (RadioGroup) findViewById(R.id.RegisterRadioGroup);
        RegisterRadiogroup.clearCheck();

        //DatePicker On DOB

        RegisterEtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        RegisterEtBirthdate.setText(dayofMonth+"/"+(month+1)+"/"+year);

                    }
                },year,month,day);
                picker.show();

            }
        });

        Button Registerbtn;

        Registerbtn = (Button) findViewById(R.id.RegisterBtn);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = RegisterRadiogroup.getCheckedRadioButtonId();
                RegisterRadioButtonSelected = findViewById(selectedGenderId);

                //obtain Enterd Data

                String txtFullName = RegisterEtFullname.getText().toString();
                String addhaarNum = addhaar.getText().toString();
                String txtEmail = RegisterEtEmail.getText().toString();
                String txtDob = RegisterEtBirthdate.getText().toString();
                String txtMobile = RegisterEtMobile.getText().toString();
                String txtPwd =  RegisterEtPassword.getText().toString();
                String txtConfirmPwd = RegisterEtConfirmPassword.getText().toString();
                String txtGender; //Can't Obtain the Value Before Verifying if any Button Was Selected or not

                //Validation Mobile Number Useing matche and Pattern

                String mobileReg = "[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileReg);
                mobileMatcher = mobilePattern.matcher(txtMobile);

                if(TextUtils.isEmpty(txtFullName)){
                    Toast.makeText(Registration.this, "Please Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    RegisterEtFullname.setError("Full Name is required");
                    RegisterEtFullname.requestFocus();
                }else if(TextUtils.isEmpty(txtEmail)){
                    Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    RegisterEtEmail.setError("Email Required");
                    RegisterEtEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
                    Toast.makeText(Registration.this, "Please Re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    RegisterEtEmail.setError("Valid Email Required");
                    RegisterEtEmail.requestFocus();
                }else if(TextUtils.isEmpty(txtDob)){
                    Toast.makeText(Registration.this, "Please Enter Date Of Birth", Toast.LENGTH_SHORT).show();
                    RegisterEtBirthdate.setError("Required Birth Date");
                    RegisterEtBirthdate.requestFocus();
                }else if(RegisterRadiogroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(Registration.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                    RegisterRadioButtonSelected.setError("Gender Required");
                    RegisterRadioButtonSelected.requestFocus();
                }else if(TextUtils.isEmpty(txtMobile)){
                    Toast.makeText(Registration.this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                    RegisterEtMobile.setError("Mobile Number Required");
                    RegisterEtMobile.requestFocus();
                }else if(txtMobile.length() != 10){
                    Toast.makeText(Registration.this, "Please re-Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    RegisterEtMobile.setError("Mobile Number Should Be 10 Digit Only");
                    RegisterEtMobile.requestFocus();
                }else if(!mobileMatcher.find()){
                    Toast.makeText(Registration.this, "Please re-enter Mobile number", Toast.LENGTH_SHORT).show();
                    RegisterEtMobile.setError("Invalid Mobile Number");
                    RegisterEtMobile.requestFocus();
                }
                else if(TextUtils.isEmpty(txtPwd))
                {
                    Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    RegisterEtPassword.setError("Password Required");
                    RegisterEtPassword.requestFocus();
                }else if(txtPwd.length() < 6){
                    Toast.makeText(Registration.this, "Password Should be At least 6 Digit", Toast.LENGTH_SHORT).show();
                    RegisterEtPassword.setError("Password Is Too Weak");
                    RegisterEtPassword.requestFocus();
                }else if(TextUtils.isEmpty(txtConfirmPwd))
                {
                    Toast.makeText(Registration.this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
                    RegisterEtConfirmPassword.setError("Password Confirmation is Required");
                    RegisterEtConfirmPassword.requestFocus();

                    //clear the entered password
                    RegisterEtPassword.clearComposingText();
                    RegisterEtConfirmPassword.clearComposingText();
                }else {
                    txtGender = RegisterRadioButtonSelected.getText().toString();

                    registerUser(txtFullName,txtEmail,txtDob,txtGender,txtMobile,txtPwd,addhaarNum);
                }



            }
        });


    }

    private void registerUser(String txtFullName, String txtEmail, String txtDob, String txtGender, String txtMobile, String txtPwd, String addhaarNum) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(txtEmail,txtPwd).addOnCompleteListener(Registration.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Registration.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration.this,MainActivity.class);

                            startActivity(intent);
                            FirebaseUser firebaseUser = auth.getCurrentUser();



                            //Enter User Data into the firebase realtime database
                            ReadWriteUserDetails writeUserDetalis = new ReadWriteUserDetails(txtFullName, txtDob, txtGender, txtMobile,txtPwd);

                            //Extracting User Referencs from database for "Registered Users"

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetalis).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //send verification email

                                        //open after user profile successfully registered

                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(Registration.this, "user registration successful please verify your email ", Toast.LENGTH_SHORT).show();

                                    }else {
                                        Toast.makeText(Registration.this, "Registration Failed.Please Try Again", Toast.LENGTH_SHORT).show();

                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }else{
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                RegisterEtPassword.setError("Your Password is to weak. kindly use alphabets, numbers and special characters");
                                RegisterEtPassword.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                RegisterEtPassword.setError("Your email is invalid or already in use.kindly re-enter");
                                RegisterEtPassword.requestFocus();
                            }catch (FirebaseAuthUserCollisionException e){
                                RegisterEtPassword.setError("user is already register with this email");
                                RegisterEtPassword.requestFocus();
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}