package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private BottomNavigationView bottomNav;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scheduleAlarm();
        }
        handleIntent(getIntent());
    }

    public void handleIntent(Intent intent){
        String frag = intent.getStringExtra("fragmentToOpen");
        Log.d("wow", intent.getAction());
        if (frag != null){
            Log.d("wow", "intent ai yahan");
            Log.d("wow", frag);
            if (frag.equals("OpenToDo")) {
                Log.d("wow", "we got here");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodoFragment()).commit();
                bottomNav.setSelectedItemId(R.id.nav_todo);
            }
        }

        else if (intent.getAction().equals("OpenToday")){
            Log.d("wow", "intent ai");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment()).commit();
            bottomNav.setSelectedItemId(R.id.nav_today);
        }

        //Clicked position on the widget. Not needed but can be used in the future.
        int pos = intent.getIntExtra(TodayWidgetProvider.EXTRA_ITEM_POSITION, -1);
        if (pos != -1){
            Toast.makeText(this, "Position clicked " + pos, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void setUpBottomNavigation(){
        bottomNav = findViewById(R.id.bottom_navigation);

        //Setting default fragment.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment()).commit();

        //Setting click listeners for the bottom navigation to switch fragments.
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                //Finding out which item was clicked.
                switch (item.getItemId()) {
                    case R.id.nav_today:
                        selectedFragment = new TodayFragment();
                        break;
                    case R.id.nav_todo:
                        selectedFragment = new TodoFragment();
                        break;
                    case R.id.nav_timetable:
                        selectedFragment = new TimeTableFragment();
                        break;
                    case R.id.nav_others:
                        selectedFragment = new OthersFragment();
                        break;
                }

                //Switching the fragment to the clicked one
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }

    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        Log.d("wow", "Step 1");

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HALF_DAY, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

}