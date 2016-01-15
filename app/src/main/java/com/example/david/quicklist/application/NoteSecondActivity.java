package com.example.david.quicklist.application;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.david.quicklist.R;
import com.example.david.quicklist.application.android.DataBaseManager;

public class NoteSecondActivity extends AppCompatActivity {
    private DataBaseManager manager;
    private Cursor listContentCursor;

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(),R.string.savingData, Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        deployUI();
    }

    private void initDB() {
        manager = new DataBaseManager(this);
        if(id() != -1) listContentCursor = manager.loadContentCursorWhereUserIdIs(id());
    }

    private int id() {
        return this.getIntent().getExtras().getInt("id");
    }

    public void closeAction(){ ///SaveButton?
        Intent intent = new Intent();
        intent.putExtra("response","closed");
        this.setResult(RESULT_OK, intent);
        finish();
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
        linearLayout.setId(R.id.lisLinearLayout);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.addView(listView());
        return linearLayout;
    }

    private ListView listView() {
        ListView listView = new ListView(this);
        listView.setId(R.id.list1);
        listView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        listView.setDividerHeight(5);
        if(id() != -1) listView.setAdapter(adapter());
        return listView;
    }

    private SimpleCursorAdapter adapter() {
        String[] from = new String[]{manager.COLUMN_NAME_ITEM_CONTENT};
        int[] to = new int[]{R.id.text3};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_item, listContentCursor,from,to,0);
        return adapter;
    }

    private RelativeLayout.LayoutParams LayoutParamsWithUpToolBarRule() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW,R.id.toolbar2);
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
        toolbar.addView(addNewListItemButton());
        return toolbar;
    }

    private ImageButton addNewListItemButton() {
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
                    //CREATE NEW LIST DISPLAY THE FORM¿?
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(getResources().getColor(R.color.colorBar));
                }
                return true;
            }
        });

        return imageButton;
    }
}
