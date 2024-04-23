package com.fareez.todolistapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskDataModel> taskList;

    // Constructor to initialize the taskList
    public TaskAdapter(List<TaskDataModel> taskList) {
        this.taskList = taskList;
    }

    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDate, tvTask, tvStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textView5);
            tvDate = itemView.findViewById(R.id.textView6);
            tvTask = itemView.findViewById(R.id.textView7);
            tvStatus = itemView.findViewById(R.id.textView8);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskDataModel task = taskList.get(position);
        holder.tvId.setText("ID: " + task.getId());
        holder.tvDate.setText("Date: " + task.getDate());
        holder.tvTask.setText("Task: " + task.getTask());
        holder.tvStatus.setText("Status: " + task.getStatus());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
