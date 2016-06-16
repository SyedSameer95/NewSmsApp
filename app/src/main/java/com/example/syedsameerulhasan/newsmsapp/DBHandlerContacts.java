package com.example.syedsameerulhasan.newsmsapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandlerContacts extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsDB.db";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTACTNUMBER = "contactnumber";


    //We need to pass database information along to superclass
    public DBHandlerContacts(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACTNUMBER + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addContact(Contacts contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACTNUMBER, contact.get_contactnumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void delContact(){
        //ContentValues values = new ContentValues();
        //values.put(COLUMN_CONTACTNUMBER, contact.get_contactnumber());
        SQLiteDatabase db = getWritableDatabase();
        //db.insert(TABLE_CONTACTS, null, values);
        //db.delete(TABLE_CONTACTS,"contactnumber=?",new String[]{""});
        db.execSQL("Delete from "+TABLE_CONTACTS+";");
        //db.delete(TABLE_CONTACTS,"contactnumber=?",new String[]{"335"});
        db.close();
    }


    public List<String> getContactNumberToArray(){
        List<String> str = new ArrayList<String>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("contactnumber")) != null) {
                str.add(c.getString(c.getColumnIndex("contactnumber")));
            }
            c.moveToNext();
        }
        db.close();
        return str;
    }


    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("contactnumber")) != null) {
                dbString += c.getString(c.getColumnIndex("contactnumber"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String GetFirstPhoneNumber(int Index)
    {
        String Phone = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " ORDER BY " + COLUMN_ID ;

        int i =0;
        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {

            if (c.getString(c.getColumnIndex(COLUMN_CONTACTNUMBER)) != null) {
                if(i == Index) {
                    Phone = c.getString(c.getColumnIndex(COLUMN_CONTACTNUMBER));
                }
            }
            i++;
            c.moveToNext();
        }
        db.close();

        return Phone;
    }


    public String comparecontact(String sender) {
        String dbString = "";
        String column1 = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT contactnumber FROM contacts WHERE contactnumber='"+sender+"'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            column1 = c.getString(c.getColumnIndex("contactnumber"));
        }
        c.close(); System.out.println(column1);
        Log.i("MyTag",column1);

        return column1;

    }
}
