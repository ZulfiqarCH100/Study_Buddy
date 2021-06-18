package com.example.studybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SQLite implements  Database{

    private Context context;
    public SQLite(Context c){
        this.context = c;
    }

    @Override
    public void SaveCourse(String name, String teacher, String section, String venue, int hour, int min, String duration) {
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Name", name);
        content.put("Teacher", teacher);
        content.put("Section", section);
        content.put("Venue", venue);
        content.put("Hour", hour);
        content.put("Minute",min);
        content.put("Duration", duration);
        db.insertWithOnConflict("Courses",null,content,SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void SaveDays(String day, String courseName){
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Day", day);
        content.put("CourseName", courseName);
        db.insertWithOnConflict("TimeTable", null, content, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public ArrayList<Course> GetTodaysClasses(String day) {
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CourseName FROM TimeTable WHERE Day = '" + day + "'";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> courseList = new ArrayList<>();
        while(cursor.moveToNext()){
            courseList.add(cursor.getString(0));
        }
        ArrayList<Course> courses = new ArrayList<>();
        String query2;
        Cursor cursor2;
        for (int i= 0; i< courseList.size();i++){
            query2 = "SELECT * FROM Courses WHERE Name = '" + courseList.get(i) + "'";
            cursor2 = db.rawQuery(query2,  null);
            while(cursor2.moveToNext()){
                String courseName = cursor2.getString(0);
                String teacherName = cursor2.getString(1);
                String section = cursor2.getString(2);
                String venue = cursor2.getString(3);
                int hour = cursor2.getInt(4);
                int min = cursor2.getInt(5);
                String duration = cursor2.getString(6);
                courses.add(new Course(courseName, teacherName, section, hour, min, duration, venue));
                Log.d("Test", "The query returned Course: " + courseName);
            }
        }
        return courses;
    }
}
