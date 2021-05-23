package com.example.mobileservicess;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class PingAsync extends AsyncTask<Void, Void, String> {
    private static final String LOG_TAG = PingAsync.class.getSimpleName();

    public PingAsync() {
    }

    @Override
    protected String doInBackground(Void... voids) {
         /*try {
             Thread.sleep(600000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }*/
        return NetworkUtils.getBackendInfo();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
           // JSONObject jsonObject = new JSONObject(s);
            JSONArray Array = new JSONArray(s);


                 JSONObject currentItem = Array.getJSONObject(0);

                 Log.d(LOG_TAG,"json = "+currentItem.toString());
                 Log.d(LOG_TAG,"type = "+currentItem.getString("jobType"));
                 Log.d(LOG_TAG,"host = "+currentItem.getString("host"));
                 Log.d(LOG_TAG,"count = "+currentItem.getString("count"));
                 Log.d(LOG_TAG,"packetSize = "+currentItem.getString("packetSize"));
                 Log.d(LOG_TAG,"Period = "+currentItem.getString("jobPeriod"));
                 Log.d(LOG_TAG,"date = "+currentItem.getString("date"));



                 String pingCmd = "ping  -c  " + currentItem.getString("count");
                 pingCmd = pingCmd + " -s " +currentItem.getString("packetSize");
                 pingCmd = pingCmd +" " + currentItem.getString("host");
                 String pingResult = "";
                 Runtime r = Runtime.getRuntime();
                 Process p = r.exec(pingCmd);
                 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                 String inputLine;
                 while ((inputLine = in.readLine()) != null) {
                 pingResult += inputLine;
                 }
                 in.close();
                 Log.d(LOG_TAG,"pingResult " + pingResult);
                 Service.pingResult = pingResult;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
}
