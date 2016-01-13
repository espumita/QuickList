package com.example.david.quicklist.application;

import android.app.ActionBar;
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
import java.util.List;
import java.util.Map;

public class ApplicationMainActivity extends AppCompatActivity {
    private DataBaseManager manager;
    private Cursor cursor;
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
       //manager.insert("1name","1content");
       //manager.insert("2name","2content");
       //manager.insert("3name","3content");
       //manager.insert("4name","4content");
       //manager.insert("5name","5content");
        cursor = manager.loadAnnotationsCursor();
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
        //ScrollView scrollView = new ScrollView(this);
        //scrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        //RadioGroup radioGroup = new RadioGroup(this);
        //radioGroup.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        //scrollView.addView(radioGroup);
        //linearLayout.addView(scrollView);
        //radioGroup.addView(oneButton(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //for(String annotation : annotationList )radioGroup.addView(annotation(annotation),new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
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
        String[] from = new String[]{manager.COLUMN_NAME_NAME,manager.COLUMN_NAME_CONTENT};
        int[] to = new int[]{R.id.text1,R.id.text2};
        adapter = new SimpleCursorAdapter(this,R.layout.vertical_list_item,cursor,from,to,0);
        return adapter;
    }

    public void changeVerticalListItemBackgroundColor(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.colorAnnotationTwo));
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
            //cursor = manager.query
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //adapter.changecursor(cursor)
            Toast.makeText(getApplicationContext(),"Searched!", Toast.LENGTH_SHORT).show();
        }
    }
}
