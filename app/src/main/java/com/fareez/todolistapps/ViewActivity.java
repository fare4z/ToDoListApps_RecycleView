package com.fareez.todolistapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    TaskDataSource taskDataSource;

    private List<TaskDataModel> taskList; // Declare taskList globally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<>(); // Initialize taskList
        taskAdapter = new TaskAdapter(taskList); // Pass taskList to adapter
        recyclerView.setAdapter(taskAdapter);


        taskDataSource = new TaskDataSource(getApplicationContext());
        taskDataSource.open();

        Cursor cursor = taskDataSource.getAllTaskData();

        StringBuilder outputText = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String task = cursor.getString(cursor.getColumnIndex("task"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                taskList.add(new TaskDataModel(id, date, task, status));
            } while (cursor.moveToNext());
        }


        taskAdapter.notifyDataSetChanged();
    }
}