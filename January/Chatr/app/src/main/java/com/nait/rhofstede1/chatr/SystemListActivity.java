package com.nait.rhofstede1.chatr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class SystemListActivity extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFromServer();
    }

    private void getFromServer()
    {
        //instanciate array
        BufferedReader in = null;
        //declare array list
        ArrayList chatr = new ArrayList();

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            //execute request
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //get all info
            String line = "";
            while ((line = in.readLine()) != null)
            {
                chatr.add(line);
            }
            in.close();
            //create the adapter to read the list
            ArrayAdapter<ArrayList> adapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, chatr);
            //this adapter is tied to this list view and this data.
            this.setListAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: "+ e, Toast.LENGTH_LONG).show();
        }
    }
}
