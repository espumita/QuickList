package com.example.david.quicklist.controler;

import android.widget.TextView;


import com.example.david.quicklist.R;
import com.example.david.quicklist.application.ApplicationMainActivity;

import java.util.Map;


public class PressButtonCommand implements Command {
    private ApplicationMainActivity mainActivityReference;
    private Map<String, TextView> components;

    public PressButtonCommand(ApplicationMainActivity mainActivityReference, Map<String, TextView> components) {
        this.mainActivityReference = mainActivityReference;
        this.components = components;
    }

    @Override
    public void execute() {
        components.get("oneButton").setBackgroundColor(mainActivityReference.getColorFromResource(R.color.colorAnnotationTwo));
        components.get("oneButton").setTextColor(mainActivityReference.getColorFromResource(R.color.colorDarkLetters));
    }


}
