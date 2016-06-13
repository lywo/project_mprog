package com.example.lydia.savethenight;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 3-6-2016.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "date.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_QUESTION = "questionData";
    private static final String TABLE_NAME_SETTINGS = "settingsData";
    public static final String COLUMN_ITEM_QUESTION = "question";
    public static final String COLUMN_ITEM_STATUS = "status";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_NUMBER =  "number";
    public static final String COLUMN_ITEM_SMS = "sms";
    public static final String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_NAME_QUESTION + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
            + " , " + COLUMN_ITEM_QUESTION + " TEXT"+ " , " + COLUMN_ITEM_STATUS
            + " INTEGER " + ")";
    public static final  String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_NAME_SETTINGS + "("
            + COLUMN_ITEM_NAME + " TEXT" + " , " + COLUMN_ITEM_NUMBER +  " TEXT" + " , "
            + COLUMN_ITEM_SMS + " TEXT" + ")";
    public static String[] questions = null;
    private final Context context;

    // constructor
    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        questions = context.getResources().getStringArray(R.array.questions);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);

        for (String question : questions) {
            db.execSQL("INSERT INTO questionData (question, status) VALUES ('" + question + "', 0)");
        }

        db.execSQL("INSERT INTO settingsData (name, number, sms) VALUES ('', '', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tables
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_SETTINGS);
        onCreate(db);
    }

    // CRUD methods
    public void saveContact(String name, String phoneNumber){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE settingsData SET name = '" + name + "', number = '" + phoneNumber + "'");
    }

    public String getName(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_NAME + " FROM " + TABLE_NAME_SETTINGS, null);
        String result=null;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("name"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getNumber(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_NUMBER + " FROM " + TABLE_NAME_SETTINGS, null);
        String result=null;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("number"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    public void saveSMS(String smsText){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE settingsData SET sms = '" + smsText + "'");
    }

    public String getSMS(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_SMS + " FROM " + TABLE_NAME_SETTINGS, null);
        String result=null;
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("sms"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return result;
    }

    public void setBoolTrue(String newFavouriteQuestion){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_QUESTION + " SET " + COLUMN_ITEM_STATUS + " = 1 WHERE "
                + COLUMN_ITEM_QUESTION + " = '" + newFavouriteQuestion + "'");
    }

    public void setBoolFalse (String oldFavouriteQuestion){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_QUESTION + " SET " + COLUMN_ITEM_STATUS + " = 0 WHERE "
                + COLUMN_ITEM_QUESTION + " = '" + oldFavouriteQuestion + "'");
    }

    public ArrayList<String> getFavouriteQuestions(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_QUESTION + " FROM " + TABLE_NAME_QUESTION
                + " WHERE " + COLUMN_ITEM_STATUS + " = 1", null);
        ArrayList<String> result = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String favouriteQuestion = cursor.getString(cursor.getColumnIndex("question"));
                result.add(favouriteQuestion);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return result;
    }

    ArrayList<String> loadQuestions (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_QUESTION + " FROM " + TABLE_NAME_QUESTION, null);
        ArrayList<String> result = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String question = cursor.getString(cursor.getColumnIndex("question"));
                result.add(question);
                cursor.moveToNext();
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }
}
