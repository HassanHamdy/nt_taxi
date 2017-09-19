package com.example.owner.nt_taxi.View;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.owner.nt_taxi.BuildConfig;
import com.example.owner.nt_taxi.Model.constants_class;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Owner on 8/6/2017.
 */

public class BaseFragment extends Fragment {

    public SharedPreferences.Editor editor;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (BuildConfig.category == 0) {
            editor = getActivity().getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
        } else {
            editor = getActivity().getSharedPreferences(constants_class.DriverPREFERENCES, MODE_PRIVATE).edit();
        }
    }
}
