package com.example.david.quicklist.application.android;

import android.content.Context;
import android.widget.Button;

import com.example.david.quicklist.R;
import com.example.david.quicklist.model.TaskList;
import com.example.david.quicklist.view.TaskListDisplay;

public class TaskListLayout extends Button implements TaskListDisplay{
    private TaskList taskList;

    public TaskListLayout(Context context,TaskList taskList) {
        super(context);
        this.taskList = taskList;
        deployUI();
    }

    private void deployUI() {
        this.setBackgroundColor(getResources().getColor(R.color.annotationYellow));
        this.setText(taskList.name());
    }


    @Override
    public TaskList taskList() {
        return taskList;
    }
}
