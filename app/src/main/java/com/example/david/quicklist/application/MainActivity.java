package com.example.david.quicklist.application;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.david.quicklist.R;
import com.example.david.quicklist.controler.Command;
import com.example.david.quicklist.controler.PressButtonCommand;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MainActivity mainActivityReference;
    private Map<String, Command> commands;
    private Map<String,View> components;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityReference = this;
        commands = new HashMap<>();
        components = new HashMap<>();
        deployUi();
        createCommands();
    }

    private void createCommands() {
        commands.put("pressButton", new PressButtonCommand(mainActivityReference, components));
    }

    private void deployUi() {
        RelativeLayout mainLayOut = new RelativeLayout(this);
        mainLayOut.setBackgroundColor(Color.BLUE);
        mainLayOut.addView(oneButton());
        setContentView(mainLayOut);
    }

    private Button oneButton() {
        Button button = new Button(this);
        components.put("oneButton", button);
        button.setText(R.string.button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commands.get("pressButton").execute();
            }
        });
        return button;
    }

    public int getColorFromResource(int colorReference){
        return getResources().getColor(colorReference);
    }
}
