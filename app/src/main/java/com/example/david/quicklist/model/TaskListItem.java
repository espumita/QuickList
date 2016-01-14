package com.example.david.quicklist.model;

public class TaskListItem {
    private boolean status;
    private String content;

    public TaskListItem(String content) {
        this.content = content;
        this.status = false;
    }
}
