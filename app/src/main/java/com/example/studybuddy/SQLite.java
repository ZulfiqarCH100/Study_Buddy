package com.example.studybuddy;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;

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

        //Notify all widgets.
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, TodayWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetTodayList);
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

    @Override
    public void SaveToDo(String task, int startHour, int startMinute, int startDay, int startMonth, int startYear) {
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Task", task);
        content.put("Hour", startHour);
        content.put("Minute", startMinute);
        content.put("Day", startDay);
        content.put("Month", startMonth);
        content.put("Year", startYear);
        db.insertWithOnConflict("ToDo", null, content, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public ArrayList<ToDo> getAllToDo() {
        ArrayList<ToDo> list = new ArrayList<>();
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * from ToDo";
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String task = cursor.getString(1);
            int dueHour = cursor.getInt(2);
            int dueMin = cursor.getInt(3);
            int dueDay = cursor.getInt(4);
            int dueMonth = cursor.getInt(5);
            int dueYear = cursor.getInt(6);
            list.add(new ToDo(ID, task, dueDay, dueMonth, dueYear, dueHour, dueMin));
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public void removeToDo(int id) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "DELETE FROM ToDo WHERE ID = " + id;
        db.execSQL(query);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getTodaysToDoCount() {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        LocalDate current = LocalDate.now();
        Month Month = current.getMonth();
        int month = Month.getValue();
        int year = current.getYear();
        int day = current.getDayOfMonth();
        String query = "Select * from ToDo where Day = "+day+ " and Month = "+month+" and Year = "+year;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}
