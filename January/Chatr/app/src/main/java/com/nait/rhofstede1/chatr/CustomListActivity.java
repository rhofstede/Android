package com.nait.rhofstede1.chatr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomListActivity extends ListActivity
{
    //define terms for the array passing information between app and database
    public static final String SENDER = "sender";
    public static final String TEXT = "text";
    public static final String THE_DATE = "theDate";

    ArrayList<HashMap<String, String>> chatrArray = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        displayChatr();
    }

    private void displayChatr()
    {
        //create arrays with the keys and array with values
        String[] keys = new String[]{SENDER, TEXT, THE_DATE};
        int[] ids = new int[]{R.id.text_view_custom_row_sender, R.id.text_view_custom_row_message, R.id.text_view_custom_row_date};
        //create adapter
        SimpleAdapter adapter = new SimpleAdapter(this, chatrArray, R.layout.custom_row, keys, ids);
        //get info into the arrays
        populateList();
        //attach adapter to listview
        this.setListAdapter(adapter);
    }

    private void populateList()
    {
        BufferedReader in = null;

        try
        {
            //import gradle scripts, build gradle (module app), and add useLibrary 'org.apache.http.legacy'
            //this is deprecated code. use the proper libraries when building
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();

            request.setURI(new URI("http://www.youcode.ca/JitterServlet")); //universal resource identifier
            //response holds all the stuff
            HttpResponse response = client.execute(request);
            //parse stuff into the buffered reader
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while((line = in.readLine()) != null)
            {
                //the strings will come in the order: sender, text, date, sender, text, date
                HashMap<String, String> tempMap = new HashMap<String, String>();
                tempMap.put(SENDER,line);

                line = in.readLine();
                tempMap.put(TEXT, line);

                line = in.readLine();
                tempMap.put(THE_DATE, line);

                chatrArray.add(tempMap);
            }
            in.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
