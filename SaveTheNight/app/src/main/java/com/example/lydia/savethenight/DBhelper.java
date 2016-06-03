package com.example.lydia.savethenight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lydia on 3-6-2016.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "date.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_QUESTION = "questionData";
    private static final String TABLE_NAME_SETTINGS = "settingsData";
    public static final String COLUMN_ITEM_ID = "id";
    public static final String COLUMN_ITEM_QUESTION = "question";
    public static final String COLUMN_ITEM_STATUS = "status";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_NUMBER =  "number";
    public static final String COLUMN_ITEM_SMS = "sms";

    private final Context context;

    // constructor
    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create Question table
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_NAME_QUESTION + "(" + COLUMN_ITEM_ID
                + " INTEGER" + " , " + COLUMN_ITEM_QUESTION + " TEXT"+ " , " + COLUMN_ITEM_STATUS
                + " INTEGER " + ")";
        db.execSQL(CREATE_QUESTION_TABLE);

        // create Settings table
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_NAME_SETTINGS + "(" + COLUMN_ITEM_NAME
                + " TEXT" + " , " + COLUMN_ITEM_NUMBER +  " TEXT" + " , " + COLUMN_ITEM_SMS
                + " TEXT" + ")";
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // create Question table
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_NAME_QUESTION + "(" + COLUMN_ITEM_ID
                + " INTEGER" + " , " + COLUMN_ITEM_QUESTION + " TEXT"+ " , " + COLUMN_ITEM_STATUS
                + " INTEGER " + ")";
        db.execSQL(CREATE_QUESTION_TABLE);

        // create Settings table
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_NAME_SETTINGS + "(" + COLUMN_ITEM_NAME
                + " TEXT" + " , " + COLUMN_ITEM_NUMBER +  " TEXT" + " , " + COLUMN_ITEM_SMS
                + " TEXT" + ")";
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    // CRUD methods

}
