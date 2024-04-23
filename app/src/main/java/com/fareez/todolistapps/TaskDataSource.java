package com.fareez.todolistapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TaskDataSource {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    public TaskDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public long insertTaskData(String date, String task, String status) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("task", task);
        values.put("status", status);

        return db.insert("tblTask",null,values);
    }
    public Cursor getAllTaskData() {
        String[] allColumns = {"id","date", "task", "status"};
return db.query("tblTask",allColumns, null,null,null,null,null);
    }

    public int updateData(Integer id, String date, String task, String status) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("task", task);
        values.put("status", status);
       return db.update("tblTask",values,"id = ?",
               new String[]{String.valueOf(id)});
    }

    public void deleteData(Integer id) {
        db.delete("tblTask", "id = " + id, null);
        db.delete("sqlite_sequence", "name = " + "'tblTask'", null);


    }

}
