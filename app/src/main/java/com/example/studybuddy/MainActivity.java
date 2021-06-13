package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation(){
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

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
}