package com.example.lydia.savethenight;
/*
 * Created by Lydia on 3-6-2016.
 * DBhelper.java called
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/*
 * constructs a SQLiteDatabase when app is installed (never again until uninstalled)
 * makes 2 tables and various functions to alter those.
 */
public class DBhelper extends SQLiteOpenHelper {

    // define Strings and queries
    private static final String DATABASE_NAME = "date.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_QUESTION = "questionData";
    private static final String TABLE_NAME_SETTINGS = "settingsData";
    public static final String COLUMN_ITEM_QUESTION = "question";
    public static final String COLUMN_ITEM_STATUS = "status";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_NUMBER =  "number";
    public static final String COLUMN_ITEM_SMS = "sms";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_NAME_QUESTION + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
            + " , " + COLUMN_ITEM_QUESTION + " TEXT"+ " , " + COLUMN_ITEM_STATUS
            + " INTEGER " + ")";
    public static final  String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_NAME_SETTINGS + "("
            + COLUMN_ITEM_NAME + " TEXT" + " , " + COLUMN_ITEM_NUMBER +  " TEXT" + " , "
            + COLUMN_ITEM_SMS + " TEXT" + ")";
    public static String[] questions = null;

    // constructor
    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        questions = context.getResources().getStringArray(R.array.questions);
    }

    /*
    Execute create tables queries and insert questions in database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);

        for (String question : questions) {
            db.execSQL("INSERT INTO questionData (question, status) VALUES ('" + question + "', 0)");
        }
        db.execSQL("INSERT INTO settingsData (name, number, sms) VALUES ('', '', '')");
    }

    /*
    If database exists, drop and to go onCreate()
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tables
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_SETTINGS);
        onCreate(db);
    }

    // CRUD methods

    /*
    Save selected Strings name and phone number in database
     */
    public void saveContact(String name, String phoneNumber){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE settingsData SET name = '" + name + "', number = '" + phoneNumber + "'");
    }

    /*
    Get saved name from selected contact from database to show in View
    Return name from saved contact as a String
     */
    public String getName(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_NAME + " FROM " + TABLE_NAME_SETTINGS, null);
        String result = null;

        // loop over  cursor
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("name"));
                cursor.moveToNext();
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }

    /*
    Get saved number from selected contact from database to send sms
    Return saved number as a String
     */
    public String getNumber(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_NUMBER + " FROM " + TABLE_NAME_SETTINGS, null);
        String result=null;

        // loop over cursor
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("number"));
                cursor.moveToNext();
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }

    /*
    Save sms text from input String in database
     */
    public void saveSMS(String smsText){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE settingsData SET sms = '" + smsText + "'");
    }

    /*
    Get saved sms text from database to send sms
    Returns String with sms text
     */
    public String getSMS(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_SMS + " FROM " + TABLE_NAME_SETTINGS, null);
        String result=null;

        // loop over cursor
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("sms"));
                cursor.moveToNext();
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }

    /*
    Add question to favourites by editing boolean to true
     */
    public void setBoolTrue(String newFavouriteQuestion){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_QUESTION + " SET " + COLUMN_ITEM_STATUS + " = 1 WHERE "
                + COLUMN_ITEM_QUESTION + " = '" + newFavouriteQuestion + "'");
    }

    /*
    Delete questions from favourites by editing boolean to false
     */
    public void setBoolFalse (String oldFavouriteQuestion){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_QUESTION + " SET " + COLUMN_ITEM_STATUS + " = 0 WHERE "
                + COLUMN_ITEM_QUESTION + " = '" + oldFavouriteQuestion + "'");
    }

    /*
    Get all questions where boolean is true to get ArrayList favourite questions
    Return ArrayList with all favourite questions in Strings
     */
    public ArrayList<String> getFavouriteQuestions(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_QUESTION + " FROM " + TABLE_NAME_QUESTION
                + " WHERE " + COLUMN_ITEM_STATUS + " = 1", null);
        ArrayList<String> result = new ArrayList<>();

        // loop over cursor
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String favouriteQuestion = cursor.getString(cursor.getColumnIndex("question"));
                result.add(favouriteQuestion);
                cursor.moveToNext();
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }

    /*
    Get all questions stored in database
    Return ArrayList with questions in Strings
     */
    ArrayList<String> loadQuestions (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_QUESTION + " FROM " + TABLE_NAME_QUESTION, null);
        ArrayList<String> result = new ArrayList<>();

        // run over cursor
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

    public String getOneQuestion(int _id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ITEM_QUESTION + " FROM " + TABLE_NAME_QUESTION
                + " WHERE " + COLUMN_ITEM_ID + " = " + _id, null);
        String result = null;

        // loop over  cursor
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getString(cursor.getColumnIndex("question"));
                cursor.moveToNext();
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();

        return result;
    }
}
