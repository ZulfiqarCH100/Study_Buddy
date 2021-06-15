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
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addcourse);
//       setUpBottomNavigation();
//        addButton = findViewById(R.id.button);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
//                //startActivityForResult(intent, REQUEST_CODE);
//                startActivity(intent);
//            }
//       });
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