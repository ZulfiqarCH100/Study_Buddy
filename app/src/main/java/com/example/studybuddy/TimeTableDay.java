package com.example.studybuddy;

import java.util.ArrayList;

public class TimeTableDay {
    public String day;
    public ArrayList<Course> courses;

    TimeTableDay(String day, ArrayList<Course> list){
        this.day = day;
        courses = list;
    }


}
