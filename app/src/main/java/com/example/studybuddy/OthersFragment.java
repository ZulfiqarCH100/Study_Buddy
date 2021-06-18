package com.example.studybuddy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class OthersFragment extends Fragment {
    private NotificationManagerCompat notifManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        Button addButton = view.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddCourseActivity.class);
                startActivity(intent);
            }
        });

        Button pomodoro = view.findViewById(R.id.startPomodoro);
        pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), PomodoroActivity.class);
                startActivity(i);
            }
        });


        notifManager = NotificationManagerCompat.from(view.getContext());
        Button notifier = view.findViewById(R.id.notifier);
        notifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We send the notification here.
                //We can sent and customize the notification immediately after the function call without placing the semicolon.
                //Building and customizing the notification.
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("fragmentToOpen", "OpenToDo");
                PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Notification notification = new NotificationCompat.Builder(view.getContext(), Base.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.icon_today)
                        .setContentTitle("Test Notification")
                        .setContentText("Beep Boop")
                        .setPriority(3)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                //Sending the notification.
                notifManager.notify(1, notification); //If we send another notification with the same id, this one will be overwriten. So use alag ids for alag notifications.
            }
        });
        return view;
    }

}
