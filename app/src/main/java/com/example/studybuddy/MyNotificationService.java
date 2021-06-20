package com.example.studybuddy;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ThreadPoolExecutor;

//   JobIntentService
//Works on a seperate thread.
//No need to make the activity foreground with this.
//Kills itself after done.
// It is better ot use JobSceduler with this which is just a better Alarm Manager.

public class MyNotificationService extends JobIntentService {
    public static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyNotificationService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("wow", "Step 3");
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("fragmentToOpen", "OpenToDo");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //Check for updates.
        Database db = new SQLite(this);
        int todosDue = db.getTodaysToDoCount();
        if (todosDue == 0)
            return;

        Log.d("wow", "Due " + todosDue);

        Notification notification = new NotificationCompat.Builder(this, Base.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_today)
                .setContentTitle("You have " + todosDue + " Tasks due today!")
                .setContentText("Click here to view them")
                .setPriority(3)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        //Sending the notification.
        notifManager.notify(1, notification);
    }
}
