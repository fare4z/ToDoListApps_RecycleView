package com.fareez.todolistapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskDataModel> taskList;
    private static OnDeleteButtonClickListener onDeleteButtonClickListener;
    private static OnUpdateButtonClickListener onUpdateButtonClickListener; // Add update button click listener


    // Constructor to initialize the taskList
    public TaskAdapter(List<TaskDataModel> taskList) {
        this.taskList = taskList;
    }

    // Interface for delete button click listener
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    // Method to set delete button click listener
    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        onDeleteButtonClickListener = listener;
    }

    // Interface for update button click listener
    public interface OnUpdateButtonClickListener {
        void onUpdateButtonClick(int position);
    }

    // Method to set update button click listener
    public void setOnUpdateButtonClickListener(OnUpdateButtonClickListener listener) {
        onUpdateButtonClickListener = listener;
    }
    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDate, tvTask, tvStatus;
        Button btnDelete, btnUpdateTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textView5);
            tvDate = itemView.findViewById(R.id.textView6);
            tvTask = itemView.findViewById(R.id.textView7);
            tvStatus = itemView.findViewById(R.id.textView8);
            btnDelete = itemView.findViewById(R.id.btnDeleteList);
            btnUpdateTask = itemView.findViewById(R.id.btnUpdateTask);

            // Set click listener for delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDeleteButtonClickListener != null) {
                        onDeleteButtonClickListener.onDeleteButtonClick(position);
                    }
                }
            });
            btnUpdateTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onUpdateButtonClickListener != null) {
                        onUpdateButtonClickListener.onUpdateButtonClick(position);
                    }
                }
            });
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