package com.example.studybuddy;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.util.Calendar;

public class ToDo implements Comparable<ToDo> {
    private boolean done = false;
    private String text;
    private String color; //Color of the note.
    private int dueMonth;
    private int dueDay, dueYear;
    private int dueHour, dueMin;
    static int TotalIDs = 0;
    private int ID;
    // We dont actually need this constructor
    public ToDo(String t, int dd, int dm, int dy , int dh, int dmin){
        ID = TotalIDs + 1;
        TotalIDs++;
        text = t;
        dueMonth = dm;
        dueDay = dd;
        dueHour = dh;
        dueMin = dmin;
    }

    public ToDo(int id, String t, int dd, int dm, int dy , int dh, int dmin){
        ID = id;
        text = t;
        dueMonth = dm;
        dueDay = dd;
        dueHour = dh;
        dueMin = dmin;
        dueYear = dy;
    }
    public boolean getDone(){return done;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDueDay() {
        Calendar c = Calendar.getInstance();
        c.set(dueYear, dueMonth - 1, dueDay - 1);
        DayOfWeek dayOfWeek = DayOfWeek.of(c.get(Calendar.DAY_OF_WEEK));
        String Day = dayOfWeek.name();
        return Day;
    }
//
//    public void setDueDate(String dueDay) {
//        this.dueDay = dueDay;
//    }

    public int getIntDate(){
        return dueMin + dueHour*100 + dueDay*1000 + dueMonth*100000 + dueYear*10000000;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getID(){
        return ID;
    }

    @Override
    public int compareTo(ToDo o) {
        return this.getIntDate() - o.getIntDate();
    }
}
