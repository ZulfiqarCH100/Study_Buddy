package com.example.studybuddy;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
    String courseName, venue, duration;
    String teacherName;
    int  startHour, startMinute;
    String section;
   // ArrayList<String> days;

    public Course(String cn, String tn, String sec, int sh, int sm, String dur, String ven) {
        this.courseName = cn;
        this.duration = dur;
        this.teacherName = tn;
        this.startHour = sh;
        this.startMinute = sm;
        this.section = sec;
        this.venue = ven;
    }
        public String getTime(){
            String hour = "";
            String minute = "";

            if (startHour < 10)
                hour = "0" + String.valueOf(startHour);
            else
                hour = String.valueOf(startHour);

            if (startMinute < 10)
                minute = "0" + String.valueOf(startMinute);
            else
                minute = String.valueOf(startMinute);

            String time = (hour + ":" + minute);
            return time;
    }
}
