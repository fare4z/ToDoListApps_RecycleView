package com.fareez.todolistapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDataSource taskDataSource;
    private List<TaskDataModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        taskDataSource = new TaskDataSource(getApplicationContext());
        taskDataSource.open();

        taskAdapter.setOnDeleteButtonClickListener(new TaskAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {

                long selectedTaskId = taskList.get(position).getId();
                taskDataSource.deleteData((int) selectedTaskId);
                taskList.remove(position);
                taskAdapter.notifyItemRemoved(position);
            }
        });

        taskAdapter.setOnUpdateButtonClickListener(new TaskAdapter.OnUpdateButtonClickListener() {
            @Override
            public void onUpdateButtonClick(int position) {
                showUpdateDialog(position);
            }
        });
        // Load data dari database
        loadDataFromDatabase();
        }

    private void showUpdateDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_task, null);
        builder.setView(dialogView);

        EditText etDateUpd = dialogView.findViewById(R.id.etDateUpd);
        EditText etTaskUpd = dialogView.findViewById(R.id.etTaskUpd);
        EditText etStatusUpd = dialogView.findViewById(R.id.etStatusUpd);

        TaskDataModel task = taskList.get(position);
        etDateUpd.setText(task.getDate());
        etTaskUpd.setText(task.getTask());
        etStatusUpd.setText(task.getStatus());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String updatedDate = etDateUpd.getText().toString();
                String updatedTask = etTaskUpd.getText().toString();
                String updatedStatus = etStatusUpd.getText().toString();

                taskDataSource.updateData((int) task.getId(), updatedDate, updatedTask, updatedStatus);

                task.setDate(updatedDate);
                task.setTask(updatedTask);
                task.setStatus(updatedStatus);

                taskAdapter.notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


    private void loadDataFromDatabase() {
        Cursor cursor = taskDataSource.getAllTaskData();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                taskList.add(new TaskDataModel(id, date, task, status));
            } while (cursor.moveToNext());
        }
        taskAdapter.notifyDataSetChanged();
    }

}
