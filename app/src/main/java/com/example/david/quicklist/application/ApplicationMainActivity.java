package com.example.david.quicklist.application;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.david.quicklist.R;
import com.example.david.quicklist.application.android.AnnotationSetReader;
import com.example.david.quicklist.application.android.TaskListLayout;
import com.example.david.quicklist.controler.Command;
import com.example.david.quicklist.controler.PressButtonCommand;
import com.example.david.quicklist.model.TaskList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationMainActivity extends AppCompatActivity {
    private ApplicationMainActivity mainActivityReference;
    private Map<String, Command> commands;
    private Map<String,TextView> components;
    private List<String> annotationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityReference = this;
        commands = new HashMap<>();
        components = new HashMap<>();
        annotationList = new AnnotationSetReader().read();
        deployUi();
        deployToolBar();
        createCommands();
    }

    private View deployToolBar() {
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBar));
        toolbar.setId(R.id.toolbar);
        toolbar.setPopupTheme(R.style.toolBarStyle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLightLetters));
        toolbar.setTitle(R.string.toolBarTittle);
        return toolbar;
    }

    private void createCommands() {
        commands.put("pressButton", new PressButtonCommand(mainActivityReference, components));
    }

    private void deployUi() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.setId(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorBackGround));
        relativeLayout.addView(deployToolBar());
        LinearLayout linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);
        linearLayout.setLayoutParams(params);
        linearLayout.setId(R.id.linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5, 16, 5, 16);
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        scrollView.addView(radioGroup);
        linearLayout.addView(scrollView);
        radioGroup.addView(oneButton(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        for(String annotation : annotationList )radioGroup.addView(annotation(annotation),new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        relativeLayout.addView(linearLayout);
        setContentView(relativeLayout);
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
