package com.example.david.quicklist.controler;

import android.view.View;


import com.example.david.quicklist.R;
import com.example.david.quicklist.application.ApplicationMainActivity;

import java.util.Map;


public class PressButtonCommand implements Command {
    private ApplicationMainActivity mainActivityReference;
    private Map<String, View> components;

    public PressButtonCommand(ApplicationMainActivity mainActivityReference, Map<String, View> components) {
        this.mainActivityReference = mainActivityReference;
        this.components = components;
    }

    @Override
    public void execute() {
        components.get("oneButton").setBackgroundColor(mainActivityReference.getColorFromResource(R.color.annotationGreen));
    }


}
