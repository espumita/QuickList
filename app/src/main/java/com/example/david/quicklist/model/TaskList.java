package com.example.david.quicklist.model;

public class TaskList implements Annotation{
    private String name;

    public TaskList(String name) {
        this.name = name;
    }

    @Override
    public void name(String name) {

    }

    @Override
    public String name() {
        return name;
    }
}
