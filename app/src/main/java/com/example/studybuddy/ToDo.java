package com.example.studybuddy;

public class ToDo {
    private boolean done = false;
    private String text;
    private String color; //Color of the note.
    private String dueDate;

    public ToDo(String t, String d, String c){text = t; dueDate = d; color = c;}

    public boolean getDone(){return done;}

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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
