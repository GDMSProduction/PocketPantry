package com.softwaredev.groceryappv1;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class popup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.4),(int)(height*.2));
    }
}