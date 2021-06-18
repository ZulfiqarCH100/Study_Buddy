package com.example.studybuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Courses";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Courses (Name TEXT PRIMARY KEY, " +
                "Teacher TEXT," +
                "Section TEXT," +
                "Venue TEXT," +
                "Hour INT," +
                "Minute INT,"+
                "Duration TEXT)";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Courses");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}