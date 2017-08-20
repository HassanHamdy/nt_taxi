package com.example.owner.nt_taxi.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.nt_taxi.Model.RegistrationParser;
import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.Model.loginRootObject;
import com.example.owner.nt_taxi.R;
import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity{
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    private TextInputEditText signUpEmailEditTxt,signUpPasswordEditTxt,signUpMobileEditTxt,
            signUpUsernameEditTxt, signInEmailEditTxt,signInPasswordEditTxt;
    private RadioGroup signInCategoryRG,signUpCategoryRG;
    private RadioButton signInClientRD,signInDriverRD,signUpClientRD,signUpDriverRD;
    private String category;
    private Pattern pattern;
    private Matcher matcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });



        signInEmailEditTxt = (TextInputEditText) findViewById(R.id.signInEmail);
        signInPasswordEditTxt = (TextInputEditText) findViewById(R.id.signInPassword);
        signInCategoryRG = (RadioGroup) findViewById(R.id.signInUserCategory);
        signInClientRD = (RadioButton) findViewById(R.id.signInClient);
        signInDriverRD = (RadioButton) findViewById(R.id.signInDriver);


        signUpEmailEditTxt = (TextInputEditText) findViewById(R.id.signUpEmail);
        signUpPasswordEditTxt = (TextInputEditText) findViewById(R.id.signUpPassword);
        signUpMobileEditTxt = (TextInputEditText) findViewById(R.id.signUpMobile);
        signUpCategoryRG = (RadioGroup) findViewById(R.id.signUpUserCategory);
        signUpClientRD = (RadioButton) findViewById(R.id.signUpClient);
        signUpDriverRD = (RadioButton) findViewById(R.id.signUpDriver);
        signUpUsernameEditTxt = (TextInputEditText) findViewById(R.id.signUpUsername);


        SignInBTN_handler();
        SignOutBTN_handler();


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

                int selected_ID = signInCategoryRG.getCheckedRadioButtonId();
                if(selected_ID == R.id.signInClient){
                    category = signInClientRD.getText().toString();

                }else if(selected_ID == R.id.signInDriver){
                    category = signInDriverRD.getText().toString();
                }


                Services.Login(signInEmailEditTxt.getText().toString(),
                        signInPasswordEditTxt.getText().toString(),
                        category, new RequestCallback() {
                            @Override
                            public void Success(String response) {
                                btnSignin.setEnabled(false);

// when make class generic but should add<T> in loginRootObjectClass

//                                loginRootObject<loginInfoParser> Root = new Gson().fromJson(response,new TypeToken<loginRootObject<loginInfoParser>>(){}.getType());
                                loginRootObject Root = new Gson().fromJson(response,loginRootObject.class);
                                if(Root.getSuccess() == 1){
                                    SharedPreferences.Editor editor = getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
                                    editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                    editor.putString(constants_class.UserName, Root.getInfo().get(0).getName());
                                    editor.putString(constants_class.UserID, Root.getInfo().get(0).getId());
                                    editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                    editor.putBoolean(constants_class.isLoggedIn, true);
                                    editor.apply();
                                    startActivity(new Intent(MainActivity.this,HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();

                                }else {
                                    Toast.makeText(MainActivity.this,Root.getMessage(), Toast.LENGTH_LONG).show();
                                    btnSignin.setEnabled(true);

                                }

                            }

                            @Override
                            public void Error(Exception ex) {
                                Toast.makeText(MainActivity.this,"Network Error....!", Toast.LENGTH_LONG).show();

                            }
                        },MainActivity.this);

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

                            if(matcher.matches()){

                                Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
                                btnSignup.startAnimation(clockwise);

                                if(signUpCategoryRG.getCheckedRadioButtonId() == -1){
                                    signUpClientRD.setError("Please choose one");
                                    signUpDriverRD.setError("Please choose one");

                                }else {
                                    int selected_ID = signUpCategoryRG.getCheckedRadioButtonId();
                                    if(selected_ID == R.id.signUpClient){
                                        category = signUpClientRD.getText().toString();

                                    }else if(selected_ID == R.id.signUpDriver){
                                        category = signUpDriverRD.getText().toString();
                                    }



                                    Services.Register(signUpUsernameEditTxt.getText().toString(),
                                            signUpEmailEditTxt.getText().toString(),
                                            signUpPasswordEditTxt.getText().toString(),
                                            signUpMobileEditTxt.getText().toString(),
                                            category, new RequestCallback() {
                                                @Override
                                                public void Success(String response) {

                                                    RegistrationParser Root = new Gson().fromJson(response,RegistrationParser.class);
                                                    btnSignup.setEnabled(false);
                                                    RegistrationParser Registration = new Gson().fromJson(response,RegistrationParser.class);

                                                    if(Registration.getSuccess() == 0){

                                                        Toast.makeText(MainActivity.this,Registration.getMessage(),Toast.LENGTH_LONG).show();
                                                        btnSignup.setEnabled(true);
                                                    }else {
                                                        SharedPreferences.Editor editor = getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
                                                        editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                                        editor.putString(constants_class.UserName, Root.getInfo().get(0).getName());
                                                        editor.putString(constants_class.UserID, Root.getInfo().get(0).getId());
                                                        editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                                        editor.putBoolean(constants_class.isLoggedIn, true);
                                                        editor.apply();
                                                        startActivity(new Intent(MainActivity.this,HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                        finish();
                                                    }


                                                }

                                                @Override
                                                public void Error(Exception ex) {

                                                }
                                            }, MainActivity.this);

                                }

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


}
