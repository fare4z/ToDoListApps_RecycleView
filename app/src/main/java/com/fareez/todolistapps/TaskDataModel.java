package com.fareez.todolistapps;


public class TaskDataModel {
    private long id;
    private String date;
    private String task;
    private String status;

    public TaskDataModel() {
    }

    public TaskDataModel(long id, String date, String task, String status) {
        this.id = id;
        this.date = date;
        this.task = task;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}