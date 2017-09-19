package com.example.owner.nt_taxi.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.owner.nt_taxi.BuildConfig;
import com.example.owner.nt_taxi.Model.constants_class;

/**
 * Created by Owner on 8/6/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.category == 0) {
            editor = getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
        } else {
            editor = getSharedPreferences(constants_class.DriverPREFERENCES, MODE_PRIVATE).edit();
        }
    }
}
