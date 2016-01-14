package com.example.david.quicklist.application;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

import com.example.david.quicklist.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deployUI();
    }

    private void deployUI() {
        setContentView(relativeLayout());
    }
    private RelativeLayout relativeLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorBackGround));
        relativeLayout.addView(deployToolBar());
        relativeLayout.addView(linearLayout());
        return relativeLayout;
    }
    private LinearLayout linearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(LayoutParamsWithUpToolBarRule());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.addView(listView());
        return linearLayout;
    }

    private ListView listView() {
        ListView listView = new ListView(this);
        listView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(5);
        listView.setAdapter(adapter());
        return listView;
    }

    private ArrayAdapter adapter() {
        List<String> values = new ArrayList<>();
        values.add(getResources().getString(R.string.addNewItem));
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.new_list_item,R.id.text3,values);
        return  adapter;
    }

    public void createNewListItem(View view){

    }

    private RelativeLayout.LayoutParams LayoutParamsWithUpToolBarRule() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar2);
        return params;
    }
    private Toolbar deployToolBar() {
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBar));
        toolbar.setId(R.id.toolbar2);
        toolbar.setPopupTheme(R.style.toolBarStyle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLightLetters));
        toolbar.setTitle(R.string.list);
        //toolbar.addView(settingsButton());
        //toolbar.addView(addNewAnnotationButton());
        return toolbar;
    }
}
