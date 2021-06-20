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

        Button pomodoro = view.findViewById(R.id.pomodoroTimer);
        pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), PomodoroActivity.class);
                startActivity(i);
            }
        });

        Button gpa = view.findViewById(R.id.gpacalc);
        gpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), GpaActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

}
