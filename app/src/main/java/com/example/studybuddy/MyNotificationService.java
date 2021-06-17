package com.example.studybuddy;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationService extends IntentService {

    public MyNotificationService() {
        super("Due ToDos");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new NotificationCompat.Builder(this, Base.CHANNEL_2_ID)
                .setContentTitle("")
                .setContentText("").build();
        startForeground(2, notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("wow", "Step 3");
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("fragmentToOpen", "OpenToDo");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this, Base.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_today)
                .setContentTitle("Test Notification")
                .setContentText("Beep Boop")
                .setPriority(3)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        //Sending the notification.
        notifManager.notify(1, notification);
    }
}
