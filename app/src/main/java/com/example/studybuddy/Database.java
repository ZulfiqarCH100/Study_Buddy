package com.example.studybuddy;

import java.util.ArrayList;

public interface Database {

    public void SaveCourse(String name, String teacher, String section, String venue, int startHour, int startMinute, String duration);
    public void SaveDays(String day, String courseName);
    public ArrayList<Course> GetTodaysClasses(String day);
}
