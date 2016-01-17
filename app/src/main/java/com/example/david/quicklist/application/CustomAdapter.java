package com.example.david.quicklist.application;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class CustomAdapter extends SimpleCursorAdapter {
    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }


}
