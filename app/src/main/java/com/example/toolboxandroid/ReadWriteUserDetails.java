package com.example.toolboxandroid;

public class ReadWriteUserDetails {
    public String fullname, dob, gender, mobile,password;

    //Constructor;
    public ReadWriteUserDetails(){

    };

    public ReadWriteUserDetails(String txtFullname, String txtDob, String txtGender, String txtMobile,String txtpwd){
        this.fullname = txtFullname;
        this.dob = txtDob;
        this.gender = txtGender;
        this.mobile = txtMobile;
        this.password = txtpwd;
    }



}
