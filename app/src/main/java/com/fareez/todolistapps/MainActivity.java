package com.fareez.todolistapps;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TaskDataSource taskDataSource;
    EditText etDate, etTask, etStatus;
    Button btnSave, btnView, btnUpdate, btnDelete;
    TextView tvOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDataSource = new TaskDataSource(getApplicationContext());
        taskDataSource.open();

        etDate = findViewById(R.id.etDate);
        etTask = findViewById(R.id.etTask);
        etStatus = findViewById(R.id.etStatus);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        tvOutput = findViewById(R.id.tvOutput);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 taskDataSource.insertTaskData(etDate.getText().toString(), etTask.getText().toString(), etStatus.getText().toString());

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = taskDataSource.getAllTaskData();
                StringBuilder outputText = new StringBuilder();
                if (cursor.moveToFirst()) {
                    do {
                       long id = cursor.getLong(cursor.getColumnIndex("id"));
                       String date = cursor.getString(cursor.getColumnIndex("date"));
                       String task = cursor.getString(cursor.getColumnIndex("task"));
                       String status = cursor.getString(cursor.getColumnIndex("status"));

                       outputText.append("ID : ").append(id).append("\n")
                               .append("Date : ").append(date).append("\n")
                               .append("Task : ").append(task).append("\n")
                               .append("Status : ").append(status).append("\n");
                    } while (cursor.moveToNext());
                }
                tvOutput.setText(outputText.toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               taskDataSource.updateData(1,"22/04/2024","Lab Task 3","Done");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDataSource.deleteData(3);
            }
        });
    }




}