package com.example.syedsameerulhasan.newsmsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed Sameer Ul Hasan on 15-Jun-16.
 */
public class DBHandlerInterval extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "intervalDB.db";
    public static final String TABLE_INTERVAL = "interval";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIMEINTERVAL = "timeinterval";

    public DBHandlerInterval(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_INTERVAL + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIMEINTERVAL + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERVAL);
        onCreate(db);
    }

    //Add a new row to the database
    public void addInterval(Interval interval){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMEINTERVAL, interval.get_timeinterval());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INTERVAL, null, values);
        db.close();
    }

    //Delete a product from the database
    public void delInterval(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete from "+TABLE_INTERVAL+";");
        db.close();
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INTERVAL + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("timeinterval")) != null) {
                dbString += c.getString(c.getColumnIndex("timeinterval"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public List<String> getIntervalToArray(){
        List<String> str = new ArrayList<String>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INTERVAL + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("timeinterval")) != null) {
                str.add(c.getString(c.getColumnIndex("timeinterval")));
            }
            c.moveToNext();
        }
        db.close();
        return str;
    }

}
