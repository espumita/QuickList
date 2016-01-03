package com.example.david.quicklist.application;

import android.app.Activity;
import android.nfc.tech.NfcA;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.david.quicklist.R;
import com.example.david.quicklist.application.android.AnnotationSetReader;
import com.example.david.quicklist.application.android.TaskListLayout;
import com.example.david.quicklist.controler.Command;
import com.example.david.quicklist.controler.PressButtonCommand;
import com.example.david.quicklist.model.TaskList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationMainActivity extends Activity {
    private ApplicationMainActivity mainActivityReference;
    private Map<String, Command> commands;
    private Map<String,View> components;
    private List<String> annotationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityReference = this;
        commands = new HashMap<>();
        components = new HashMap<>();
        annotationList = new AnnotationSetReader().read();
        deployUi();
        createCommands();
    }

    private void createCommands() {
        commands.put("pressButton", new PressButtonCommand(mainActivityReference, components));
    }

    private void deployUi() {
        LinearLayout mainLayOut = new LinearLayout(this);
        mainLayOut.setOrientation(LinearLayout.VERTICAL);
        mainLayOut.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        //theme actionbar with add new anotation button
        mainLayOut.addView(oneButton(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        for(String annotation : annotationList )mainLayOut.addView(annotation(annotation),new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        setContentView(mainLayOut);
    }

    private View annotation(String name) {
        //list != note
        TaskListLayout taskListLayout = new TaskListLayout(this, new TaskList(name));
        return taskListLayout;
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
