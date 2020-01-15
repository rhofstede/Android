package com.nait.rhofstede1.chatr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hack to allow operations on the main thread.
        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        //create the buttons
        Button sendButton = findViewById(R.id.button_send);
        Button viewButton = findViewById(R.id.button_view);

        //listen for the buttons
        sendButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //find out what button was pressed
        switch (view.getId())
        {
            //add data to the database
            case R.id.button_send:
            {
                break;
            }
            //go to the viewing page
            case R.id.button_view:
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);
                break;
            }
            //this should never be reached. if something happens here you really messed up.
            default:
            {
                break;
            }
        }
    }
}
