package com.example.owner.nt_taxi.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.nt_taxi.BuildConfig;
import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.example.owner.nt_taxi.Model.RegistrationParser;
import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.Model.loginRootObject;
import com.example.owner.nt_taxi.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    private TextInputEditText signUpEmailEditTxt,signUpPasswordEditTxt,signUpMobileEditTxt,
            signUpUsernameEditTxt, signInEmailEditTxt,signInPasswordEditTxt;
    private String category;
    private Pattern pattern;
    private Matcher matcher;
    private GoogleMap googleMap;
    private MapView mapView;
    private String lat = "26.8206", lng = "30.8025";
    private SharedPreferences.Editor editor;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSigninInvoker=(TextView) findViewById(R.id.tvSigninInvoker);
        tvSignupInvoker=(TextView) findViewById(R.id.tvSignupInvoker);

        btnSignin=(Button) findViewById(R.id.btnSignin);
        btnSignup=(Button) findViewById(R.id.btnSignup);

        llSignin=(LinearLayout) findViewById(R.id.llSignin);
        llSignup=(LinearLayout) findViewById(R.id.llSignup);

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });


        signInEmailEditTxt = (TextInputEditText) findViewById(R.id.signInEmail);
        signInPasswordEditTxt = (TextInputEditText) findViewById(R.id.signInPassword);

        if (BuildConfig.category == 1) {

            category = "driver";
            setWidthPercent(llSignin, 1.00f);
            llSignup.setVisibility(View.GONE);
        } else {
            editor = getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
            category = "client";

            mapView = (MapView) findViewById(R.id.register_location);
            if (mapView != null) {
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
            }

            tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSignupForm();
                }
            });


            signUpEmailEditTxt = (TextInputEditText) findViewById(R.id.signUpEmail);
            signUpPasswordEditTxt = (TextInputEditText) findViewById(R.id.signUpPassword);
            signUpMobileEditTxt = (TextInputEditText) findViewById(R.id.signUpMobile);
            signUpUsernameEditTxt = (TextInputEditText) findViewById(R.id.signUpUsername);

            SignOutBTN_handler();

        }


        SignInBTN_handler();


    }

    public void setWidthPercent(View view, float width) {
        PercentLayoutHelper.PercentLayoutParams params = (PercentLayoutHelper.PercentLayoutParams) view.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = width;
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);
        SignInBTN_handler();


    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);

        SignOutBTN_handler();

    }

    public void SignInBTN_handler(){


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
                btnSignin.startAnimation(clockwise);


                if (signInEmailEditTxt.getText().length() > 0) {

                    if (signInPasswordEditTxt.getText().length() > 0) {

                        Services.Login(signInEmailEditTxt.getText().toString(),
                                signInPasswordEditTxt.getText().toString(), category,
                                new RequestCallback() {
                                    @Override
                                    public void Success(String response) {
                                        btnSignin.setEnabled(false);

// when make class generic but should add<T> in loginRootObjectClass

//                                loginRootObject<loginInfoParser> Root = new Gson().fromJson(response,new TypeToken<loginRootObject<loginInfoParser>>(){}.getType());
                                        loginRootObject Root = new Gson().fromJson(response, loginRootObject.class);
                                        if (Root.getSuccess() == 1) {

                                            editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                            editor.putString(constants_class.Name, Root.getInfo().get(0).getName());
                                            editor.putString(constants_class.ID, Root.getInfo().get(0).getId());
                                            editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                            editor.putString(constants_class.Image, Root.getInfo().get(0).getImage());
                                            editor.putString(constants_class.Email, Root.getInfo().get(0).getEmail());
                                            editor.putString(constants_class.Lat, Root.getInfo().get(0).getLatitude());
                                            editor.putString(constants_class.Long, Root.getInfo().get(0).getLongitude());
                                            editor.putString(constants_class.Category, Root.getInfo().get(0).getCategory());
                                            editor.putBoolean(constants_class.isLoggedIn, true);
                                            editor.apply();


                                            startActivity(new Intent(MainActivity.this, HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            finish();

                                        } else {
                                            Toast.makeText(MainActivity.this, Root.getMessage(), Toast.LENGTH_LONG).show();
                                            btnSignin.setEnabled(true);

                                        }

                                    }

                                    @Override
                                    public void Error(Exception ex) {
                                        Toast.makeText(MainActivity.this, "Network Error....!", Toast.LENGTH_LONG).show();

                                    }
                                }, MainActivity.this);

                    } else {
                        signInPasswordEditTxt.setError("please Enter the Password");
                    }
                } else {
                    signInEmailEditTxt.setError("please Enter the Email");
                }

            }
        });

    }

    public void SignOutBTN_handler(){

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pattern = Pattern.compile(constants_class.USERNAME_PATTERN);
                matcher = pattern.matcher(signUpUsernameEditTxt.getText().toString());

                if(matcher.matches()){

                    pattern = Pattern.compile(constants_class.EMAIL_PATTERN);
                    matcher = pattern.matcher(signUpEmailEditTxt.getText().toString());

                    if(matcher.matches()){
                        pattern = Pattern.compile(constants_class.PASSWORD_PATTERN);
                        matcher = pattern.matcher(signUpPasswordEditTxt.getText().toString());

                        if(matcher.matches()){

                            pattern = Pattern.compile(constants_class.MOBILE_PATTERN);
                            matcher = pattern.matcher(signUpMobileEditTxt.getText().toString());

                            if (true) {

                                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                                btnSignup.startAnimation(clockwise);


                                Services.Register(signUpUsernameEditTxt.getText().toString(),
                                        signUpEmailEditTxt.getText().toString(),
                                        signUpPasswordEditTxt.getText().toString(),
                                        signUpMobileEditTxt.getText().toString(), lat, lng, category,
                                        new RequestCallback() {
                                            @Override
                                            public void Success(String response) {

                                                RegistrationParser Root = new Gson().fromJson(response, RegistrationParser.class);
                                                btnSignup.setEnabled(false);
                                                RegistrationParser Registration = new Gson().fromJson(response, RegistrationParser.class);

                                                if (Registration.getSuccess() == 0) {

                                                    Toast.makeText(MainActivity.this, Registration.getMessage(), Toast.LENGTH_LONG).show();
                                                    btnSignup.setEnabled(true);
                                                } else {
                                                    editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                                    editor.putString(constants_class.Name, Root.getInfo().get(0).getName());
                                                    editor.putString(constants_class.ID, Root.getInfo().get(0).getId());
                                                    editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                                    editor.putString(constants_class.Image, Root.getInfo().get(0).getImage());
                                                    editor.putString(constants_class.Email, Root.getInfo().get(0).getEmail());
                                                    editor.putString(constants_class.Lat, Root.getInfo().get(0).getLatitude());
                                                    editor.putString(constants_class.Long, Root.getInfo().get(0).getLongitude());
                                                    editor.putString(constants_class.Category, Root.getInfo().get(0).getCategory());
                                                    editor.putBoolean(constants_class.isLoggedIn, true);
                                                    editor.apply();
                                                    startActivity(new Intent(MainActivity.this, HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                    finish();
                                                }


                                            }

                                            @Override
                                            public void Error(Exception ex) {

                                            }
                                        }, MainActivity.this);


                            }else {
                                signUpMobileEditTxt.setError("Please Enter right fromat of mobile number");
                            }

                        }else {
                            signUpPasswordEditTxt.setError("Please Enter correct password");
                        }

                    }else {
                        signUpEmailEditTxt.setError("Please Enter correct Email");
                    }

                }else {
                    signUpUsernameEditTxt.setError("Please Enter Right Username");
                }




            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(MainActivity.this);
        }

        this.googleMap = googleMap;

        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            googleMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }

        goToLocationZoom(Double.valueOf(lat), Double.valueOf(lng), 13);

        this.googleMap.setOnMapLongClickListener(this);

//        this.googleMap.getUiSettings().setAllGesturesEnabled(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(MainActivity.this, "This App require location permission to be granted", Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {

        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);


        LatLng ll = new LatLng(lat, lng);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.addMarker(new MarkerOptions().position(ll).title(stateName + " " + cityName + ", " + countryName))
                .showInfoWindow();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.moveCamera(update);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        googleMap.clear();
        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);
        goToLocationZoom(latLng.latitude, latLng.longitude, 13);
    }
}
