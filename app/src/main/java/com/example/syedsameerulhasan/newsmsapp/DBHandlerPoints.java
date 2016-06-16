package com.example.syedsameerulhasan.newsmsapp;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandlerPoints extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pointsDB.db";
    public static final String TABLE_POINTS = "points";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_PHONENUMBER = "PhoneNumber";


    public DBHandlerPoints(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_POINTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LATITUDE + " TEXT, " +
                COLUMN_LONGITUDE + " TEXT, " +
                COLUMN_PHONENUMBER + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addpoints (Points point){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, point.get_latitude());
        values.put(COLUMN_LONGITUDE, point.get_longitude());
        values.put(COLUMN_PHONENUMBER, point.get_PhoneNumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_POINTS, null, values);
        db.close();
    }

/*    //Delete a product from the database
    public void deleteProduct(String idp){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_POINTS + " WHERE " + COLUMN_IDP + "=\"" + idp + "\";");
    }*/


    public ArrayList<Points> getPointS(String PHONENUMBER)
    {
        ArrayList<Points> point = new ArrayList<Points>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_POINTS+ " WHERE " + COLUMN_PHONENUMBER + " = '" + PHONENUMBER + "'";
        Log.i("Query",query);
        Points myPoints;
        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_LATITUDE)) != null) {

                myPoints = new Points(c.getString(c.getColumnIndex(COLUMN_LATITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_LONGITUDE)),
                        c.getString(c.getColumnIndex(COLUMN_PHONENUMBER)));
                point.add(myPoints);
            }
            c.moveToNext();
        }
        db.close();
        return point;
    }

    public ArrayList<Points> getPointsfromDatabase(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String pointsquery = "SELECT * FROM " + TABLE_POINTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(pointsquery, null);
        //Move to the first row in your results
        c.moveToFirst();

        ArrayList<Points> points = new ArrayList<Points>();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("latitude")) != null) {
                Points point = new Points(c.getString(c.getColumnIndex("latitude")),c.getString(c.getColumnIndex("longitude")),c.getString(c.getColumnIndex(COLUMN_PHONENUMBER)) );
                points.add(point);
                /*dbString += c.getString(c.getColumnIndex("latitude"));
                dbString += " : ";
                        dbString += c.getString(c.getColumnIndex("longitude"));
                dbString += "\n";*/
            }
            c.moveToNext();
        }
        db.close();
        return points;
    }

    public String PointsdatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_POINTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("latitude")) != null) {
                dbString += c.getString(c.getColumnIndex("latitude"));
                dbString += " : ";
                dbString += c.getString(c.getColumnIndex("longitude"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
