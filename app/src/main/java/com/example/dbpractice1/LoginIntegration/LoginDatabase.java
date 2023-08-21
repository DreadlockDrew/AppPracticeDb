package com.example.dbpractice1.LoginIntegration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;

    // defines the name of the table that will hold user information
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" + ")";

    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override//Create relevant tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override //Use this to perform a hard reset on the users table.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert a new user into the database
    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    // Check if a user with the given username exists in the database
    public boolean doesUserExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    // Get the password for a given username
    public String getStoredPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_PASSWORD},
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null
        );

        String storedPassword = "";

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex >= 0) {
                storedPassword = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close();
        return storedPassword;
    }

}
