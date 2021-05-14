package com.example.mobileservicess;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    public NetworkUtils() {

    }

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getBackendInfo()
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString;
        try{

            Uri builtUri = Uri.parse("http://10.0.2.2:5000/getjobs")
                    .buildUpon()
                    .build();

            Log.d(LOG_TAG,"Connecting to http://10.0.2.2:5000/getjobs");

            URL requestUrl = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.d(LOG_TAG,"Connected");

            InputStream inputStream = urlConnection.getInputStream();
           // StringBuffer buffer = new StringBuffer();
            StringBuilder buffer = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null)
            {
                buffer.append(line);
                buffer.append("\n");
            }
            JSONString=buffer.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,"JSON - prezemen");
        return JSONString;
    }
}


