package com.istinye.week12savedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public static final String[] columnNames = new String[] { DataBaseHelper.STUDENT_ID, DataBaseHelper.STUDENT_NAME, DataBaseHelper.STUDENT_SURNAME };

    public DBManager(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return;
    }

    public void close(){
        dbHelper.close();;
    }

    public long insert(String name, String surname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.STUDENT_NAME, name);
        contentValues.put(DataBaseHelper.STUDENT_SURNAME, surname);
        return database.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
    }

    public Cursor fetch(){
        Cursor cursor = database.query(dbHelper.TABLE_NAME, columnNames, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String newName, String newSurname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.STUDENT_NAME, newName);
        contentValues.put(DataBaseHelper.STUDENT_SURNAME, newSurname);

        return database.update(dbHelper.TABLE_NAME, contentValues, DataBaseHelper.STUDENT_ID + "=" + _id, null);
    }

    public void delete(long _id) {
        database.delete(dbHelper.TABLE_NAME, DataBaseHelper.STUDENT_ID + "=" + _id, null);
    }


}
