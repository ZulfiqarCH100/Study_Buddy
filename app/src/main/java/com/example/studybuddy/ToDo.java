package com.example.studybuddy;

public class ToDo {
    private boolean done = false;
    private String text;
    private String color; //Color of the note.
    private String dueMonth;
    private String dueDay;
    private int dueHour, dueMin;

    public ToDo(String t, String dm, String dd, int dh, int dmin, String c){
        text = t;
        dueMonth = dm;
        dueDay = dd;
        dueHour = dh;
        dueMin = dmin;
        color = c;
    }

    public boolean getDone(){return done;}

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDate(String dueDay) {
        this.dueDay = dueDay;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getText() {
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
}
