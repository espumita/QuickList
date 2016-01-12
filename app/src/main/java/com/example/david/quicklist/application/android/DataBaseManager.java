package com.example.david.quicklist.application.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
    public static final String TABLE_NAME = "annotations";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static final String CREATE_TABLE = "create table "+TABLE_NAME+ " ("
            +COLUMN_NAME_ID+" integer primary key autoincrement,"
            +COLUMN_NAME_NAME+" text not null,"
            +COLUMN_NAME_CONTENT+" text);";
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    private ContentValues contentValues(String name, String content){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME,name);
        values.put(COLUMN_NAME_CONTENT,content);
        return values;
    }

    public void insert(String name,String content){
        db.insert(TABLE_NAME, null, contentValues(name, content));
    }
    public void insertSQL(String name,String content){
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (NULL,'" + name + "','" + content + "');");
    }

    public void delete(String name){
        db.delete(TABLE_NAME, COLUMN_NAME_NAME + "=?", new String[]{name});
    }

    public void updateContent(String name,String content){
        db.update(TABLE_NAME, contentValues(name, content), COLUMN_NAME_NAME + "=?", new String[]{name});
    }

    public Cursor loadAnnotationsCursor(){
        String[] columns = new String[]{COLUMN_NAME_ID,COLUMN_NAME_NAME,COLUMN_NAME_CONTENT};
        return db.query(TABLE_NAME,columns,null,null,null,null,null);
    }

}
