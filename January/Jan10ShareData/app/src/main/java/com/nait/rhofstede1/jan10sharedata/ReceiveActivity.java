package com.nait.rhofstede1.jan10sharedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String prefix = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        TextView textBox = findViewById(R.id.text_view_receive_data);
        textBox.setText(prefix + data);
    }
}
