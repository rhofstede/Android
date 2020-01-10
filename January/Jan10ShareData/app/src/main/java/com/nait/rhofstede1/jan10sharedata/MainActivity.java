package com.nait.rhofstede1.jan10sharedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.button_send_data); //instanciates the button class
        sendButton.setOnClickListener(this); //pops up the onclick method when clicked. this means current class
    }

    @Override
    public void onClick(View v)
    {
        EditText textBox = findViewById(R.id.edit_text_data); //gets the requested textbox
        String data = textBox.getText().toString(); //gets the content of the textbox

        Intent intent = new Intent(this, ReceiveActivity.class); //creates a new message. first parameter is the class we start in, second parameter is the class we're going to

        Bundle bundle = new Bundle(); // instanciates bundle
        bundle.putString("PREFIX", "From main activity: "); //key value pair
        bundle.putString("DATA", data); //key value pair

        intent.putExtras(bundle); //put bundle into message
        startActivity(intent); //creates new window with message

        //Toast.makeText(this,"u typed " + data, Toast.LENGTH_LONG).show(); //creates a pop up.
    }
}
