package com.fareez.todolistapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        btnDelete = findViewById(R.id.btnDelete);
        tvOutput = findViewById(R.id.tvOutput);

        loadDataFromDatabase();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDataSource.insertTaskData(
                        etDate.getText().toString(),
                        etTask.getText().toString(),
                        etStatus.getText().toString()
                );

                etDate.setText(null);
                etTask.setText(null);
                etStatus.setText(null);

                loadDataFromDatabase();

                Toast.makeText(MainActivity.this, "A new task has been inserted", Toast.LENGTH_SHORT).show();

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDataSource.clearData();
                loadDataFromDatabase();
                Toast.makeText(MainActivity.this, "All data has been removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataFromDatabase() {
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
                        .append("Status : ").append(status).append("\n").append("\n");

            } while (cursor.moveToNext());
        }

        tvOutput.setText(outputText);

    }
}