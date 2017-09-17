package com.example.owner.nt_taxi.View.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyEditText1 extends android.support.v7.widget.AppCompatEditText {

    public MyEditText1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText1(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(tf);
        }
    }

}