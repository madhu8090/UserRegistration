package com.example.userregisteration.helper;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.userregisteration.model.User;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserDetails.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    private static final String COLUMN_USER_LAST_NAME = "user_last_name";
    private static final String COLUMN_USER_DOB = "user_dob";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_SKILLS = "user_skills";
    private static final String COLUMN_USER_MOBILE = "user_mobile";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRST_NAME + " TEXT," + COLUMN_USER_LAST_NAME + " TEXT,"
            + COLUMN_USER_DOB + " TEXT," + COLUMN_USER_GENDER + " TEXT," + COLUMN_USER_SKILLS + " TEXT," + COLUMN_USER_MOBILE + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_USER_TABLE);

        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getfName());
        values.put(COLUMN_USER_LAST_NAME, user.getlName());
        values.put(COLUMN_USER_DOB, user.getDob());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_SKILLS, user.getSkills());
        values.put(COLUMN_USER_MOBILE, user.getMobile());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public User getUserByEmail(String email) {
        User user = new User();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USER_EMAIL + " = '" + email + "'";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                user.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setfName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setlName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOB)));
                user.setMobile(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MOBILE)));
                user.setSkills(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SKILLS)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_FIRST_NAME, user.getfName());
//        values.put(COLUMN_USER_LAST_NAME, user.getlName());
        values.put(COLUMN_USER_DOB, user.getDob());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_SKILLS, user.getSkills());
//        values.put(COLUMN_USER_EMAIL, user.getEmail());
//        values.put(COLUMN_USER_MOBILE, user.getMobile());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
