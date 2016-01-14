package com.example.david.quicklist.model;

import java.util.List;

public class TaskListAnnotation {
    private Integer id;
    private String name;
    private List<TaskListItem> contentList;

    public TaskListAnnotation(Integer id, String name, List<TaskListItem> contentList) {
        this.id = id;
        this.name = name;
        this.contentList = contentList;
    }
}
