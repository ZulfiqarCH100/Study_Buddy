package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
        handleIntent(getIntent());
    }

    public void handleIntent(Intent intent){
        String frag = intent.getStringExtra("fragmentToOpen");
        if (frag != null){
            Log.d("wow", frag);
            if (frag.equals("OpenToDo")){
                Log.d("wow", "we got here");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodoFragment()).commit();
                bottomNav.setSelectedItemId(R.id.nav_todo);
            }
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE){
//            if(resultCode == RESULT_OK){
//               Log.d("Test", "OK");
//            }
//        }
//    }
}