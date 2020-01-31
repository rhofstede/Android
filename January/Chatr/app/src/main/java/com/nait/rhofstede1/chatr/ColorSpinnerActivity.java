package com.nait.rhofstede1.chatr;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ColorSpinnerActivity extends AppCompatActivity
{
    public static final String TAG = "ColorSpinnerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_spinner);

        Spinner colorSpinner = findViewById(R.id.color_spinner);
        //implicit instantiation
        colorSpinner.setOnItemSelectedListener(new MyColorSpinnerListener());
        //or explicitly create a listener and pass it in
    }
}
class MyColorSpinnerListener implements OnItemSelectedListener
{
    //first method is automatically called
    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int position, long id)
    {
        View linearLayout = (View)spinner.getParent();
        String bgColor = spinner.getResources().getStringArray(R.array.colour_values)[position];
        linearLayout.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //for empty list if dynamically populated
    }
}