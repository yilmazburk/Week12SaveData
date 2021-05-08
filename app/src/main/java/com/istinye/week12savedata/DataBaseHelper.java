package com.istinye.week12savedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "STUDENT_DATABASE.DB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "STUDENTS";

    public static final String STUDENT_ID = "_id";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_SURNAME = "surname";

    private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDENT_NAME + " TEXT NOT NULL, "
            + STUDENT_SURNAME + " TEXT);" ;



    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
