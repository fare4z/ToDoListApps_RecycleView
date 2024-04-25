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
        loadDataFromDatabase();

        etDate = findViewById(R.id.etDate);
        etTask = findViewById(R.id.etTask);
        etStatus = findViewById(R.id.etStatus);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);

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

                Toast.makeText(MainActivity.this, "New Task Inserted", Toast.LENGTH_SHORT).show();

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
            }
        });
    }

    private void loadDataFromDatabase() {
        Cursor cursor = taskDataSource.getAllTaskData();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String task = cursor.getString(cursor.getColumnIndex("task"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

            } while (cursor.moveToNext());
        }

    }
}