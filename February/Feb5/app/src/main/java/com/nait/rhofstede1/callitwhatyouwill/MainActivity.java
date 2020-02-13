package com.nait.rhofstede1.callitwhatyouwill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity
{

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

        ArrayList array = new ArrayList();
        populateArray(array);

        //second parameter not used here (must be int)
        MySpinAdapter adapter = new MySpinAdapter(this, 1, array);
        Spinner spinner = findViewById(R.id.spinner_chatr);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new MyItemListener(this));
    }

    private void populateArray(ArrayList array)
    {
        BufferedReader in = null;

        try
        {
            //import gradle scripts, build gradle (module app), and add useLibrary 'org.apache.http.legacy'
            //this is deprecated code. use the proper libraries when building
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();

            request.setURI(new URI("http://www.youcode.ca/JitterServlet")); //universal resource identifier
            //get the stuff from the internet server we put it in
            client.execute(request);
            //response holds all the stuff
            HttpResponse response = client.execute(request);
            //parse stuff into the buffered reader
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while((line = in.readLine()) != null)
            {
                Chat temp = new Chat();
                temp.setChatSender(line);

                line = in.readLine();
                temp.setChatContent(line);

                line = in.readLine();
                temp.setChatDate(line);

                array.add(temp);
            }
            in.close();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
