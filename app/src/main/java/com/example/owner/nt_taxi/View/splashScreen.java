package com.example.owner.nt_taxi.View;

import android.content.Intent;
import android.os.Bundle;

import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.R;

public class splashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        constants_class.sharedPreferences = getSharedPreferences(constants_class.MyPREFERENCES,MODE_PRIVATE);


        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);

                    String token = constants_class.sharedPreferences.getString(constants_class.Token,null);
                    if(token == null || !constants_class.sharedPreferences.getBoolean(constants_class.isLoggedIn, false)){
                        startActivity(new Intent(splashScreen.this,MainActivity.class));
                    }else {
                        startActivity(new Intent(splashScreen.this,HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
