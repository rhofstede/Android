package com.nait.rhofstede1.labonerosemary;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    //resources not needed in xml - push to database
    public static final String REVIEWER = "REVIEWER";
    public static final String REVIEW = "REVIEW";
    public static final String CATEGORY = "CATEGORY";
    public static final String NOMINEE = "NOMINEE";
    public static final String PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate main view
        mainView = findViewById(R.id.linear_layout_main);
        //set user preferences
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String bgColor = settings.getString(getResources().getString(R.string.preference_key_main_bg_color),"#74593D");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        //hack to allow operations on the main thread.
        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        //create the buttons
        Button sendButton = findViewById(R.id.button_send);

        //listen for the buttons
        sendButton.setOnClickListener(this);

        //listen for preference changes
        settings.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onClick(View view)
    {
        //get nominee contents
        EditText textBoxNominee = findViewById(R.id.edit_text_nominee);
        String nominee = textBoxNominee.getText().toString();

        //get review contents
        EditText textBoxReview = findViewById(R.id.edit_text_message);
        String review = textBoxReview.getText().toString();

        //get category
        RadioGroup group = findViewById(R.id.radio_group_categories); //get radio group info
        int buttonID = group.getCheckedRadioButtonId(); //get the button id
        RadioButton button = (RadioButton)findViewById(buttonID); //get the correct button
        String category = (String) button.getTag(); //get the tag from that button

        postReview(nominee, review, category);
        textBoxNominee.setText("");
        textBoxReview.setText("");
    }

    private void postReview(String nominee, String review, String category)
    {
        //get username
        String username = settings.getString(getResources().getString(R.string.preference_key_username),"unknown_user");

        try
        {
            //create
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<>();
            //pass in data
            formParameters.add(new BasicNameValuePair(NOMINEE,nominee));
            formParameters.add(new BasicNameValuePair(REVIEW,review));
            formParameters.add(new BasicNameValuePair(CATEGORY,category));
            formParameters.add(new BasicNameValuePair(REVIEWER, username));
            formParameters.add(new BasicNameValuePair(PASSWORD, "oscar275"));
            //encode for internet
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            //send to database
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = settings.getString(getResources().getString(R.string.preference_key_main_bg_color),"#74593D");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_preferences:
            {
                //go to preferences
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_item_view_review:
            {
                //go to reviews
                Intent intent = new Intent(this, ViewActivity.class);
                startActivity(intent);
                break;
            }
            default:
            {
                //if the code is reached i messed up really badly
                break;
            }
        }
        return true;
    }
}
