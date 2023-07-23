package com.example.wisebridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Dictionary;
import java.util.Hashtable;

public class LoginController extends SQLiteOpenHelper {

    private static final String DB_NAME = "WiseBridge";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "LoginData";

    private Context context;


    public LoginController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        context = context;
    }

    public boolean isTableExists() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '"+TABLE_NAME+"'";

        try (Cursor cursor = db.rawQuery(query, null)) {
            if(cursor!=null) {
                return cursor.getCount() > 0;
            }
            return false;
        }
    }

    public Dictionary<Integer, String> getData(){
        Dictionary<Integer, String> data = new Hashtable();
        if (isTableExists()) {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "SELECT * FROM " + TABLE_NAME + " WHERE login_status='1'";

            Cursor cursorCourses = db.rawQuery(query, null);

            if (cursorCourses.moveToFirst()) {
                do {
                    for (int i = 0; i < 3; i++){
                        data.put(i, cursorCourses.getString(i));
                    }
                } while (cursorCourses.moveToNext());
            }
            cursorCourses.close();
        }
        return data;
    }

    public void createTable(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + "user_id TEXT PRIMARY KEY,"
                + "user_type TEXT,"
                + "login_status TEXT)";
        db.execSQL(query);
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void addUserData(Dictionary<String, String> user_data){
        SQLiteDatabase db = this.getWritableDatabase();

        if (!isTableExists()){
            createTable(db);
        }

        ContentValues values = new ContentValues();
        values.put("user_id", user_data.get("user_id"));
        values.put("user_type", user_data.get("user_type"));
        values.put("login_status", user_data.get("login_status"));

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
