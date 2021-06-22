package com.example.studybuddy;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

//In the new APIs notifications can only be sent through channels. This base class runs before any activity and defines
//our notification channels.

public class Base extends Application {
    public static final String CHANNEL_1_ID = "notificationChannel";
    public static final String CHANNEL_2_ID = "notificationChannel2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //making the notification channel.
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Test Notification2", NotificationManager.IMPORTANCE_LOW);

            //Properties of the notification.
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setDescription("This is Beep Boop Notifier");

            //Giving the channel to the notification manager so it can activate it.
            NotificationManager manager = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel2);
            //Now we register this class in the manifest so this runs at the start of the app. DONE.
        }
    }
}
