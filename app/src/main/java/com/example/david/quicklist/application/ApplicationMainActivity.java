package com.example.david.quicklist.application;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.quicklist.R;
import com.example.david.quicklist.application.android.DataBaseManager;
import com.example.david.quicklist.controler.Command;
import com.example.david.quicklist.controler.PressButtonCommand;

import java.util.HashMap;
import java.util.Map;

public class ApplicationMainActivity extends AppCompatActivity {
    private DataBaseManager manager;
    private Cursor annotationsCursor;
    private SimpleCursorAdapter adapter;
    private ApplicationMainActivity mainActivityReference;
    private Map<String, Command> commands;
    private Map<String,TextView> components;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityReference = this;
        commands = new HashMap<>();
        components = new HashMap<>();
        initDB();
        deployUi();
        deployToolBar();
        createCommands();
    }

    private void initDB() {
        manager = new DataBaseManager(this);
        test();
        annotationsCursor = manager.loadAnnotationsCursor();
    }

    private void test() {
        manager.annotationsTableInsert("1name");
        manager.annotationsTableInsert("3name");
        manager.annotationsTableInsert("3name");
        manager.annotationsTableInsert("4name");
        manager.annotationsTableInsert("5name");

       manager.contentTableInsert(2, "step_2", "true");
       manager.contentTableInsert(2, "step_2", "true");
       manager.contentTableInsert(2, "step_2", "true");
       manager.contentTableInsert(3, "step_3", "true");
       manager.contentTableInsert(3, "step_3", "true");
       manager.contentTableInsert(3, "step_3", "true");


          //manager.contentTableDelete(3);
          //manager.annotationsTableDelete("3name");
    }

    private View deployToolBar() {
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBar));
        toolbar.setId(R.id.toolbar);
        toolbar.setPopupTheme(R.style.toolBarStyle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLightLetters));
        toolbar.setTitle(R.string.toolBarTittle);
        toolbar.addView(settingsButton());
        toolbar.addView(addNewAnnotationButton());
        return toolbar;
    }

    private ImageButton settingsButton() {
        ImageButton imageButton = new ImageButton(this);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        imageButton.setPadding(5, 5, 5, 5);
        imageButton.setLayoutParams(params);
        imageButton.setBackgroundColor(getResources().getColor(R.color.colorBar));
        imageButton.setImageResource(R.drawable.menu);
        return imageButton;
    }

    private ImageButton addNewAnnotationButton() {
        ImageButton imageButton = new ImageButton(this);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        imageButton.setPadding(5, 5, 5, 5);
        imageButton.setLayoutParams(params);
        imageButton.setBackgroundColor(getResources().getColor(R.color.colorBar));
        imageButton.setImageResource(android.R.drawable.ic_input_add);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(getResources().getColor(R.color.colorOnPressedButton));
                    secondActivity(-1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(getResources().getColor(R.color.colorBar));
                }
                return true;
            }
        });

        return imageButton;
    }

    private void secondActivity(int id) {
        Intent intent = new Intent(ApplicationMainActivity.this, NoteSecondActivity.class);
        intent.putExtra("id",id);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            Toast.makeText(getApplicationContext(),"Should Refresh", Toast.LENGTH_SHORT).show();
            if(resultCode == this.RESULT_OK){
                //manage data from second activity
            }
        }
    }

    private void createCommands() {
        commands.put("pressButton", new PressButtonCommand(mainActivityReference, components));
    }

    private void deployUi() {
        setContentView(relativeLayout());
    }

    private RelativeLayout relativeLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.setId(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorBackGround));
        relativeLayout.addView(deployToolBar());
        relativeLayout.addView(linearLayout());
        return relativeLayout;
    }

    private LinearLayout linearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(LayoutParamsWithBellowToolBarRule());
        linearLayout.setId(R.id.linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.addView(listView());
        return linearLayout;
    }

    private RelativeLayout.LayoutParams LayoutParamsWithBellowToolBarRule() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);
        return params;
    }

    private ListView listView() {
        ListView listView = new ListView(this);
        listView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(5);
        listView.setAdapter(adapter());
        return listView;
    }

    private SimpleCursorAdapter adapter() {
        String[] from = new String[]{manager.COLUMN_NAME_ID,manager.COLUMN_NAME_NAME};
        int[] to = new int[]{R.id.invisibleId,R.id.text1};
        adapter = new SimpleCursorAdapter(this,R.layout.annotation, annotationsCursor,from,to,0);
        return adapter;
    }

    public void changeVerticalListItemBackgroundColor(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.colorAnnotationTwo));
        secondActivity(Integer.parseInt(((TextView) view.findViewById(R.id.invisibleId)).getText().toString()));
    }


    public int getColorFromResource(int colorReference){
        return getResources().getColor(colorReference);
    }

    private class BackgroundTask extends AsyncTask<Void,Void,Void> {
        //se la llamaria con new BackgroundTask().execute();
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Searching...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //annotationsCursor = manager.query
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //adapter.changecursor(annotationsCursor)
            Toast.makeText(getApplicationContext(),"Searched!", Toast.LENGTH_SHORT).show();
        }
    }
}
