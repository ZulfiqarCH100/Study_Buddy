package com.example.studybuddy;

import java.util.ArrayList;

public interface Database {

    public void SaveCourse(String name, String teacher, String section, String venue, int startHour, int startMinute, String duration);
    public void SaveDays(String day, String courseName);
    public ArrayList<Course> GetTodaysClasses(String day);
    public void SaveToDo(String task, int startHour, int startMinute, int startDay, int startMonth, int startYear);
    public ArrayList<ToDo> getAllToDo();
    public void removeToDo(int id);
    public int getTodaysToDoCount();
}
