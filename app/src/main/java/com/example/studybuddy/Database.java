package com.example.studybuddy;

public interface Database {

    public void SaveCourse(String name, String teacher, String section, String venue, int startHour, int startMinute, String duration);

}
