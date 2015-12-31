package com.example.david.quicklist.controler;

import android.view.View;


import com.example.david.quicklist.R;
import com.example.david.quicklist.application.MainActivity;

import java.util.Map;


public class PressButtonCommand implements Command {
    private MainActivity mainActivityReference;
    private Map<String, View> components;

    public PressButtonCommand(MainActivity mainActivityReference, Map<String, View> components) {
        this.mainActivityReference = mainActivityReference;
        this.components = components;
    }

    @Override
    public void execute() {
        components.get("oneButton").setBackgroundColor(mainActivityReference.getColorFromResource(R.color.VerdeMio));
    }


}
