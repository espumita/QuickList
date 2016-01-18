package com.example.david.quicklist.application;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class CustomAdapter extends ResourceCursorAdapter {
    protected int[] mFrom;
    protected int[] mTo;
    private int color1;
    private int color2;
    private int mStringConversionColumn = -1;
    private CursorToStringConverter mCursorToStringConverter;
    private ViewBinder mViewBinder;
    String[] mOriginalFrom;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from,int[] to, int flags, int color1,int color2) {
        super(context, layout, c, flags);
        this.color1=color1;
        mTo = to;
        mOriginalFrom = from;
        this.color2 = color2;
        findColumns(c, from);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewBinder binder = mViewBinder;
        final int count = mTo.length;
        final int[] from = mFrom;
        final int[] to = mTo;
        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, cursor, from[i]);
                }

                if (!bound) {
                    String text = cursor.getString(from[i]);
                    if (text == null) {
                        text = "";
                    }

                    if (v instanceof TextView) {
                        setViewText((TextView) v, text);
                        if(i == 1){
                            if(text.equals("1")){
                                view.setBackgroundColor(color1);
                            }else{
                                if(text.equals("0")){
                                    view.setBackgroundColor(color2);
                                }
                            }
                        }
                    } else if (v instanceof ImageView) {
                        setViewImage((ImageView) v, text);
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this CustomAdapter");
                    }
                }
            }
        }
    }

    public ViewBinder getViewBinder() {
        return mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public int getStringConversionColumn() {
        return mStringConversionColumn;
    }

    public void setStringConversionColumn(int stringConversionColumn) {
        mStringConversionColumn = stringConversionColumn;
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return mCursorToStringConverter;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        mCursorToStringConverter = cursorToStringConverter;
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        if (mCursorToStringConverter != null) {
            return mCursorToStringConverter.convertToString(cursor);
        } else if (mStringConversionColumn > -1) {
            return cursor.getString(mStringConversionColumn);
        }

        return super.convertToString(cursor);
    }

    private void findColumns(Cursor c, String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        findColumns(c, mOriginalFrom);
        return super.swapCursor(c);
    }

    public void changeCursorAndColumns(Cursor c, String[] from, int[] to) {
        mOriginalFrom = from;
        mTo = to;
        findColumns(c, mOriginalFrom);
        super.changeCursor(c);
    }

    public static interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int columnIndex);
    }

    public static interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

}
