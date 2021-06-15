package com.example.studybuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        createView();
    }

    private void createView(){
        setContentView(R.layout.addcourse);
    }
}
