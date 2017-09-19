package com.example.owner.nt_taxi.Model;


import android.content.SharedPreferences;

public class constants_class {
    public static final String MyPREFERENCES = "UserData";
    public static final String DriverPREFERENCES = "DriverData";
    public static final String ID = "ID";
    public static final String Name = "Name";
    public static final String Token = "token";
    public static final String Number = "MobileNumber";
    public static final String isLoggedIn = "LoginIn";
    public static final String Image = "image";
    public static final String Email = "email";
    public static final String Lat = "latitude";
    public static final String Long = "longitude";
    public static final String Category = "category";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * ^                 # start-of-string
     (?=.*[0-9])       # a digit must occur at least once
     (?=.*[a-z])       # a lower case letter must occur at least once
     (?=.*[A-Z])       # an upper case letter must occur at least once
     (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
     (?=\\S+$)          # no whitespace allowed in the entire string
     .{4,}             # anything, at least six places though
     $                 # end-of-string
     */

    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";
    public static final String USERNAME_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*";
    public static final String MOBILE_PATTERN =  "^\\(?(?=.*[+])(\\d{3,4})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    public static SharedPreferences sharedPreferences;
}
