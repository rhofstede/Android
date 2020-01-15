package com.nait.rhofstede1.chatr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        //get data from database
        getFromServer();
    }

    private void getFromServer()
    {
        BufferedReader in = null;
        TextView textBox = findViewById(R.id.text_view_chatr);

        try
        {
            //import gradle scripts, build gradle (module app), and add useLibrary 'org.apache.http.legacy'
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();

            request.setURI(new URI("http://www.youcode.ca/JSONServlet")); //universal resource identifier
            //get the stuff from the internet server we put it in
            client.execute(request);
            //response holds all the stuff
            HttpResponse response = client.execute(request);
            //parse stuff into the buffered reader
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while((line = in.readLine()) != null)
            {
                textBox.append(line + "\n");
            }
            in.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
