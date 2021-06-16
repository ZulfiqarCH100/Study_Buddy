package com.example.studybuddy;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//In the new APIs notifications can only be sent through channels. This base class runs before any activity and defines
//our notification channels.

public class Base extends Application {
    public static final String CHANNEL_1_ID = "notificationChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //making the notification channel.
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
            //Properties of the notification.
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setDescription("This is Beep Boop Notifier");

            //Giving the channel to the notification manager so it can activate it.
            NotificationManager manager = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel);
            //Now we register this class in the manifest so this runs at the start of the app. DONE.
        }
    }
}
