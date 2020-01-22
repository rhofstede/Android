package com.nait.rhofstede1.chatr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //draw(inflate) menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_item_view_text_view:
            {
                //go to the text view class
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_view_system_list:
            {
                //go to the system list view class
                Intent intent = new Intent(this, SystemListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_view_custom_list:
            {
                //go to the custom list view class
                Intent intent = new Intent(this, CustomListActivity.class);
                startActivity(intent);
                break;
            }
            default:
            {
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {
        //find out what button was pressed
        switch (view.getId())
        {
            //add data to the database
            case R.id.button_send:
            {
                //get message contents
                EditText textBox = findViewById(R.id.edit_text_message);
                String chatr = textBox.getText().toString();
                postToServer(chatr);
                textBox.setText(""); //set to empty string so user knows it worked
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);
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

    private void postToServer(String chatr)
    {
        try
        {
            //create variables
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            //pass in data
            formParameters.add(new BasicNameValuePair("DATA", chatr));
            formParameters.add(new BasicNameValuePair("LOGIN_NAME", "bubble_elevator"));
            //encode for internet
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            //put contents into database
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
