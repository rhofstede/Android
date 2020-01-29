package com.nait.rhofstede1.labonerosemary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
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
    public static final String USERNAME = "username";
    public static final String REVIEW = "review";
    public static final String CATEGORY = "category";
    public static final String NOMINEE = "nominee";

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
        //get radio group info
        RadioGroup radioCategoryGroup = findViewById(R.id.radio_group_categories);

        //get nominee contents
        EditText textBoxNominee = findViewById(R.id.edit_text_nominee);
        String nominee = textBoxNominee.getText().toString();

        //get review contents
        EditText textBoxReview = findViewById(R.id.edit_text_message);
        String review = textBoxReview.getText().toString();

        //get category
        int selectedCategoryID = radioCategoryGroup.getCheckedRadioButtonId();
        String category = "";
        switch (selectedCategoryID)
        {
            case R.id.radio_picture:
            {
                category = "film";
                break;
            }
            case R.id.radio_actor:
            {
                category = "actor";
                break;
            }
            case R.id.radio_actress:
            {
                category = "actress";
                break;
            }
            case R.id.radio_editing:
            {
                category = "editing";
                break;
            }
            case R.id.radio_effects:
            {
                category = "effects";
                break;
            }
            default:
            {
                break;
            }
        }

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
            formParameters.add(new BasicNameValuePair(USERNAME, username));
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
}
