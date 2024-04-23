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

        // Initialize RecyclerView and adapter
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
                // Get the ID of the selected task
                long selectedTaskId = taskList.get(position).getId();
                // Delete the task with the selected ID from the database
                taskDataSource.deleteData((int) selectedTaskId);
                // Remove the task from the list and update the RecyclerView
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

        // Initialize EditText fields
        EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        EditText editTextTask = dialogView.findViewById(R.id.editTextTask);
        EditText editTextStatus = dialogView.findViewById(R.id.editTextStatus);

        // Set current task information in EditText fields
        TaskDataModel task = taskList.get(position);
        editTextDate.setText(task.getDate());
        editTextTask.setText(task.getTask());
        editTextStatus.setText(task.getStatus());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get updated task information from EditText fields
                String updatedDate = editTextDate.getText().toString();
                String updatedTask = editTextTask.getText().toString();
                String updatedStatus = editTextStatus.getText().toString();

                // Update the task in the database
                taskDataSource.updateData((int) task.getId(), updatedDate, updatedTask, updatedStatus);

                // Update the task in the list
                task.setDate(updatedDate);
                task.setTask(updatedTask);
                task.setStatus(updatedStatus);

                // Notify the adapter of the data change
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
