package com.example.david.quicklist.application.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
    public static final String TABLE_NAME_ANNOTATIONS = "annotations";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String CREATE_TABLE_ANNOTATIONS = "create table if not exists "+ TABLE_NAME_ANNOTATIONS + " ("
            +COLUMN_NAME_ID+" integer primary key autoincrement,"
            +COLUMN_NAME_NAME+" text not null);";
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    public static final String TABLE_NAME_CONTENT = "content";
    public static final String COLUMN_NAME_USER_ID = "userId";
    public static final String COLUMN_NAME_ITEM_CONTENT = "itemContent";
    public static final String COLUMN_NAME_ITEM_STATUS = "itemStatus";
    public static final String CREATE_TABLE_CONTENT = "create table if not exists "+ TABLE_NAME_CONTENT + " ("
            +COLUMN_NAME_ID+" integer primary key autoincrement,"
            +COLUMN_NAME_USER_ID+" integer not null,"
            +COLUMN_NAME_ITEM_CONTENT+" text not null,"
            +COLUMN_NAME_ITEM_STATUS+" text not null,"
        +"foreign key("+COLUMN_NAME_USER_ID+") references  "+TABLE_NAME_ANNOTATIONS+"("+COLUMN_NAME_ID+")"
    +");";

    public DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    private ContentValues annotationsContentValues(String name){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, name);
        return values;
    }

    public void annotationsTableInsert(String name){
        db.insert(TABLE_NAME_ANNOTATIONS, null, annotationsContentValues(name));
    }
    public void annotationsTableDelete(String name){
        db.delete(TABLE_NAME_ANNOTATIONS, COLUMN_NAME_NAME + "=?", new String[]{name});
    }

    public void annotationsTableUpdateContent(String name){
        db.update(TABLE_NAME_ANNOTATIONS, annotationsContentValues(name), COLUMN_NAME_NAME + "=?", new String[]{name});
    }

    public Cursor loadAnnotationsCursor(){
        String[] columns = new String[]{COLUMN_NAME_ID,COLUMN_NAME_NAME};
        return db.query(TABLE_NAME_ANNOTATIONS,columns,null,null,null,null,null);
    }

    public void contentTableInsert(Integer userID,String itemContent,String itemStatus){
        db.insert(TABLE_NAME_CONTENT, null, contentContentValues(userID,itemContent,itemStatus));
    }
    private ContentValues contentContentValues(Integer userID,String itemContent,String itemStatus){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER_ID, userID);
        values.put(COLUMN_NAME_ITEM_CONTENT, itemContent);
        values.put(COLUMN_NAME_ITEM_STATUS, itemStatus);
        return values;
    }

    public Cursor loadContentCursor(Integer userID){
        String[] columns = new String[]{COLUMN_NAME_ID,COLUMN_NAME_USER_ID,COLUMN_NAME_ITEM_CONTENT,COLUMN_NAME_ITEM_STATUS};
        return db.query(TABLE_NAME_CONTENT,columns,COLUMN_NAME_USER_ID+"=?",new String[]{Integer.toString(userID)},null,null,null);
    }
}
