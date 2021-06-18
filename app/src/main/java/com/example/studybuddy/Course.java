package com.example.studybuddy;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    String courseName, venue, startHour, startMinute, duration;
    String teacherName;
    String section;
    ArrayList<String> days;

    public Course(String cn, String tn, String sec, String sh, String sm, String dur, String ven, ArrayList<String>list){
        this.courseName = cn;
        this.duration = dur;
        this.teacherName = tn;
        this.startHour = sh;
        this.startMinute = sm;
        this.section = sec;
        this.days = list;
        this.venue = ven;
    }
}
