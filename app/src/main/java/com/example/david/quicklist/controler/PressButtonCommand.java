package com.example.david.quicklist.controler;


import android.graphics.Color;
import android.view.View;

import java.util.Map;

public class PressButtonCommand implements Command {
    private Map<String, View> components;

    public PressButtonCommand(Map<String, View> components) {
        this.components = components;
    }

    @Override
    public void execute() {
        components.get("oneButton").setBackgroundColor(Color.GREEN);
    }
}
