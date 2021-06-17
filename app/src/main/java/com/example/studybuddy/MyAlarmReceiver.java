package com.example.studybuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("wow", "Step 2");
        Intent i = new Intent(context, MyNotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            context.startForegroundService(i);
        }
    }
}