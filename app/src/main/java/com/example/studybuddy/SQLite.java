package com.example.studybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLite implements  Database{

    private Context context;
    public SQLite(Context c){
        this.context = c;
    }

    @Override
    public void SaveCourse(String name, String teacher, String section) {
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Name", name);
        content.put("Teacher", teacher);
        content.put("Section", section);
        db.insertWithOnConflict("Courses",null,content,SQLiteDatabase.CONFLICT_REPLACE);
    }
}
