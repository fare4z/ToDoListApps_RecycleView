package com.fareez.todolistapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todoapps.db";
    public static final int DATABASE_VERSION = 2;

    public static String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS tblTask" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT , " +
            "date TEXT, " +
            "task TEXT, " +
            "status TEXT, name TEXT)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tblTASK");
        onCreate(db);
    }
}
