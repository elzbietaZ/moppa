package com.example.eugene.layouttest;

public class Task {
    public final int taskId;
    public final int userId;
    public final String status;
    public final int nValue;
    public final int deviceId;
    public final String error;
    public final int result;

    public Task(int taskId, int userId, String status, int nValue, int deviceId, String error, int result) {
        this.taskId = taskId;
        this.userId = userId;
        this.status = status;
        this.nValue = nValue;
        this.deviceId = deviceId;
        this.error = error;
        this.result = result;
    }

    public Task(int result, Task oldTask) {
        this.taskId = oldTask.taskId;
        this.userId = oldTask.userId;
        this.status = oldTask.status;
        this.nValue = oldTask.nValue;
        this.deviceId = oldTask.deviceId;
        this.error = null;
        this.result = result;
    }
}