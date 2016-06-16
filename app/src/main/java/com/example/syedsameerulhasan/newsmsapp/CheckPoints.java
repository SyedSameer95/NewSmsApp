package com.example.syedsameerulhasan.newsmsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CheckPoints extends AppCompatActivity {

    TextView printpoints;
    DBHandlerPoints dbHandler;
    private BroadcastReceiver mIntentReceiver;
    SharedPreferences preferences;
    public String FirstNumber;
    public String SecondNumber;
    public String ThirdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_points);

        Button First = (Button) findViewById(R.id.First);
        Button Second = (Button) findViewById(R.id.Second);
        Button Third = (Button) findViewById(R.id.Third);

        DBHandlerContacts dbHandler = new DBHandlerContacts(this);

        FirstNumber = dbHandler.GetFirstPhoneNumber(0);
        SecondNumber = dbHandler.GetFirstPhoneNumber(1);
        ThirdNumber = dbHandler.GetFirstPhoneNumber(2);
        First.setText(FirstNumber);
        Second.setText(SecondNumber);
        Third.setText(ThirdNumber);



        Button plotbutton = (Button) findViewById(R.id.plotbutton);

        final SharedPreferences preferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String InputString = preferences.getString("string_id","");
        Log.i("SP","InputString: "+InputString);

        if (InputString.length()>1){

            plotbutton  = (Button) findViewById(R.id.plotbutton);
            plotbutton.setBackgroundColor(Color.GREEN);

            if (InputString==FirstNumber){
                First.setBackgroundColor(Color.GREEN);
            }
            if (InputString==SecondNumber){
                Second.setBackgroundColor(Color.GREEN);
            }
            if (InputString==ThirdNumber){
                Third.setBackgroundColor(Color.GREEN);
            }
        }

        First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlotButton(v.findViewById(R.id.First), FirstNumber);
            }
        });

        Second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlotButton(v.findViewById(R.id.Second),SecondNumber);
            }
        });

        Third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlotButton(v.findViewById(R.id.Third),ThirdNumber);
            }
        });

        plotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().remove("string_id").commit();
            }
        });

    }


    public void PlotButton(View view, String PhoneNumber){
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("PhoneNumber",PhoneNumber);
        startActivity(i);
    }


}
