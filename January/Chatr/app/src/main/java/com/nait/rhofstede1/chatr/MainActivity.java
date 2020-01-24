package com.nait.rhofstede1.chatr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
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

public class MainActivity extends AppCompatActivity implements OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    //create user preferences
    SharedPreferences settings;
    //create view for editing purposes
    View mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciate main view
        mainView = findViewById(R.id.linear_layout_main);
        //set user preferences for the application
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String bgColor = settings.getString("preference_main_bg_color","#FFFF00");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

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

        //listen for preference changes
        settings.registerOnSharedPreferenceChangeListener(this);
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
            case R.id.menu_item_view_custom_list_option:
            {
                //go to the custom list option view class
                Intent intent = new Intent(this, CustomListOptionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_settings:
            {
                //go to the settings page
                Intent intent = new Intent(this, SettingsActivity.class);
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
        //get username from settings
        String username = settings.getString("preference_user_name","unknownuser");
        try
        {
            //create variables
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            //pass in data
            formParameters.add(new BasicNameValuePair("DATA", chatr));
            formParameters.add(new BasicNameValuePair("LOGIN_NAME", username));
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = settings.getString("preference_main_bg_color","#FFFF00");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}
