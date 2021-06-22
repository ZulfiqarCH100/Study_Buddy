package com.example.studybuddy;


import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QuoteThread implements Runnable {
    private String API_KEY;
    private int delay;
    Context context;

    QuoteThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL("https://quotes.rest/qod?category=inspire");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try{
            //make connection
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestMethod("GET");
            // set the content type
            urlc.setRequestProperty("Content-Type", "application/json");
            urlc.setRequestProperty("X-TheySaidSo-Api-Secret", "YOUR API KEY HERE");
            Log.d("wow", urlc.toString());
            urlc.setAllowUserInteraction(false);
            urlc.connect();

            //get result and convert it to strings
            InputStream br = new BufferedInputStream(urlc.getInputStream());
            String response = convertStreamToString(br);
            br.close();

            //Extract the required info from the JSON object. (Finally learned json parsing)
            JSONObject json = new JSONObject(response);
            JSONObject json1 = json.getJSONObject("contents");
            JSONArray json2 = json1.getJSONArray("quotes");
            JSONObject json3 = json2.getJSONObject(0);
            String quote = json3.getString("quote");
            Log.d("wow", quote);

            //Sleep for 5 seconds.
            Thread.sleep(5000);

            //Send notification to a seperate channel (so they can be turned off without turning off the useful todo Notifications)
            NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context, Base.CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.icon_today)
                    .setContentTitle("Quote of the Day!")
                    .setContentText(quote)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setPriority(1)
                    .setAutoCancel(true)
                    .build();
            //Sending the notification.
            notifManager.notify(2, notification);

        } catch (Exception e){
            Log.d("wow", "Error occured");
            Log.d("wows",e.toString());
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
