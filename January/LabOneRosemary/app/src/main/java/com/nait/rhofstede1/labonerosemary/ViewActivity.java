package com.nait.rhofstede1.labonerosemary;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity
{
    ArrayList<HashMap<String,String>> reviewArray = new ArrayList<HashMap<String,String>>();
    String category = "film";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //get user's category
        Spinner categorySpinner = findViewById(R.id.spinner_choose_category);
        categorySpinner.setOnItemSelectedListener(new MySpinnerListener());

        //display according to category
        displayReviews();
    }

    private void displayReviews()
    {
        //create arrays for the keys and values
        String[] keys = new String[]{"nominee","reviewer","review"};
        int[] ids = new int[] {R.id.text_view_row_nominee, R.id.text_view_row_reviewer, R.id.text_view_row_review};

        //create adapter
        SimpleAdapter adapter = new SimpleAdapter(this, reviewArray, R.layout.view_row, keys, ids);

        //get info into arrays
        populateArrays();

        //create listview
        ListView listview = findViewById(R.id.list_view_review_content);

        //attach adapter to listview
        listview.setAdapter(adapter);
    }

    private void populateArrays()
    {
        BufferedReader in = null;

        try
        {
            //import gradle scripts, build gradle (module app), and add useLibrary 'org.apache.http.legacy'
            //this is deprecated code. use the proper libraries when building
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();

            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet?CATEGORY=" + category)); //universal resource identifier
            //response holds all the stuff
            HttpResponse response = client.execute(request);
            //parse stuff into the buffered reader
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            String date = "";

            while((line = in.readLine()) != null)
            {
                //the strings will come in the order: date, reviewer, category, nominee, review
                //i only need reviewer, nominee, and review in the array. Category will determine if it will be displayed
                HashMap<String, String> tempMap = new HashMap<String, String>();
                date = line;
                line = in.readLine();
                tempMap.put("reviewer", line);
                line = in.readLine();
                category = line;line = in.readLine();
                line = in.readLine();
                tempMap.put("nominee", line);
                line = in.readLine();
                tempMap.put("review", line);

                reviewArray.add(tempMap);
            }
            in.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    class MySpinnerListener implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> spinner, View view, int position, long id)
        {
            category = spinner.getResources().getStringArray(R.array.category_values)[position];
            populateArrays();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
        }
    }
}