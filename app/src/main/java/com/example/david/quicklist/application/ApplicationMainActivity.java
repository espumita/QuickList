package com.example.david.quicklist.application;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
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
    private Cursor contentCursor;
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
        //manager.annotationsTableInsert("1name");
        //manager.annotationsTableInsert("2name");
        //manager.annotationsTableInsert("3name");
        //manager.annotationsTableInsert("4name");
        //manager.annotationsTableInsert("5name");
        annotationsCursor = manager.loadAnnotationsCursor();
        //ahora de cada anotación tendríamos que leer de la tabla conntent sus contenidos
        //manager.contentTableInsert(3,"step_1","true");
        //manager.contentTableInsert(3,"step_2","false");
        //manager.contentTableInsert(3,"step_3","true");
        //manager.contentTableInsert(3,"step_4","false");
        contentCursor = manager.loadContentCursor(3);

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
        String[] from = new String[]{manager.COLUMN_NAME_NAME};
        int[] to = new int[]{R.id.text1};
        adapter = new SimpleCursorAdapter(this,R.layout.vertical_list_item, annotationsCursor,from,to,0);
        return adapter;
    }

    public void changeVerticalListItemBackgroundColor(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.colorAnnotationTwo));
        startActivity(new Intent(this, NoteSecondActivity.class));
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
