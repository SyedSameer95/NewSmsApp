package com.example.syedsameerulhasan.newsmsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SaviorSettings extends AppCompatActivity {

    EditText contact1;
    EditText contact2;
    EditText contact3;
    EditText intervaltext;
    TextView printcontacts;
    DBHandlerContacts dbHandler;
    DBHandlerInterval dbHandlerInterval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savior_settings);

        contact1 = (EditText) findViewById(R.id.contact1);
        contact2 = (EditText) findViewById(R.id.contact2);
        contact3 = (EditText) findViewById(R.id.contact3);
        intervaltext = (EditText) findViewById(R.id.intervaltext);
        printcontacts = (TextView) findViewById(R.id.printcontacts);
        dbHandlerInterval =new DBHandlerInterval(this);
        dbHandler = new DBHandlerContacts(this);
        printDatabase();
    }

    public void addButtonClicked(View view){
        Contacts contact = new Contacts();
        if(contact1.getText().toString().length() > 9) {
            contact = new Contacts(contact1.getText().toString());
            dbHandler.addContact(contact);}
        if(contact2.getText().toString().length() > 9) {
            contact = new Contacts(contact2.getText().toString());
            dbHandler.addContact(contact);}
        if(contact3.getText().toString().length() > 9) {
            contact = new Contacts(contact3.getText().toString());
            dbHandler.addContact(contact);}
        Interval interval = new Interval();
        if(intervaltext.getText().toString().length() > 0) {
            interval = new Interval(intervaltext.getText().toString());
            dbHandlerInterval.addInterval(interval);}

        printDatabase();
    }

    public void deleteButtonClicked (View view){
        dbHandler.delContact();
        dbHandlerInterval.delInterval();
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        String dbStringinterval = dbHandlerInterval.databaseToString();
        printcontacts.setText(dbString+"\n"+dbStringinterval);
    }
}
