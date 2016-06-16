package com.example.syedsameerulhasan.newsmsapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SMS extends Activity{

    Button btnSendSMS;
    Button saviorbutton;
    Button trackerbutton;
    EditText txtPhoneNo;
    EditText txtMessage;
    private ToggleButton toggleStartStopButton;
    private LocationManager locManager;
    private LocationListener locListener;
    private Location mobileLocation;
    public double LONGI;
    public double LATIT;
    private BroadcastReceiver mIntentReceiver;
    DBHandlerContacts dbHandlercontacts;
    DBHandlerInterval dbHandlerInterval;
    String interval;



    public String message;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        dbHandlercontacts = new DBHandlerContacts(this);
        dbHandlerInterval = new DBHandlerInterval(this);

/*        String InputString = "How are You ?";
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("string_id", InputString);
        editor.commit();*/


        //SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
         //InputString: from the EditText


        Log.i("SP","Green Sent");



        saviorbutton = (Button) findViewById(R.id.saviorbutton);

        saviorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(getApplicationContext(), SaviorSettings.class);
                startActivity(myIntent2);
            }
        });

        trackerbutton = (Button) findViewById(R.id.trackerbutton);

        trackerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(getApplicationContext(), CheckPoints.class);
                startActivity(myIntent2);
            }
        });

        ToggleButton toggleStartStopButton = (ToggleButton) findViewById(R.id.toggleStartStopButton);
        toggleStartStopButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getCurrentLocation();
                } else {
                    locManager.removeUpdates(locListener);
                }
            }
        });

    }

    /*@Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");

                msg = msg.replace("\n", "");
                String body = msg.substring(msg.lastIndexOf(":")+1, msg.length());
                String pNumber = msg.substring(0,msg.lastIndexOf(":"));

                Log.i("Fine",pNumber);
            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }*/

    private void getCurrentLocation() {

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                mobileLocation = location;
                LATIT = mobileLocation.getLatitude();
                LONGI = mobileLocation.getLongitude();
                Geocoder geocoder;
                List<Address> addresses;

                String latitude = "Latitude: " + LATIT;
                String longitude = "Longitude: " + LONGI;
                //Toast.makeText(SMS.this, latitude +" "+ longitude, Toast.LENGTH_SHORT).show();
                //Log.i("MyTag",latitude +" "+ longitude);
                //Toast.makeText(SMS.this, "20 Secs", Toast.LENGTH_SHORT).show();
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(LATIT,LONGI,1);
                    String address = addresses.get(0).getAddressLine(0);
                    String  city = addresses.get(0).getAddressLine(1);
                    String  country = addresses.get(0).getAddressLine(2);
                    String f4 = addresses.get(0).getSubLocality();

                    Log.i("MyTag", address+ ", "+f4 + ", "+city + ", " + country);
                    //Toast.makeText(SMS.this, LATIT +" "+ LONGI, Toast.LENGTH_SHORT).show();
                    Log.i("MyTag",LATIT +" "+ LONGI);

                    List<String> ContactNumber = dbHandlercontacts.getContactNumberToArray();

                    for (int i=0; i< ContactNumber.size(); i++)
                    {

                        SENDSMSWITHADDRESS(ContactNumber.get(i), "PLEASE HELP ME! I'M AT " + address + ", " + f4 + ", " + city + ", " + country);
                        SENDSMSWITHADDRESS(ContactNumber.get(i), "http://maps.google.com/?q=" + LATIT + "," + LONGI);
                        Log.i("MyTag", "Message Sent With Address");
                    }
/*                  SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+923422066670", null,"PLEASE HELP ME! I'M AT "+address+ ", "+f4 + ", "+city + ", " + country, null, null);
                    smsManager.sendTextMessage("+923422066670", null, "http://maps.google.com/?q="+LATIT+","+LONGI, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_SHORT).show();*/
                } catch (IOException e) {
                    List<String> ContactNumber = dbHandlercontacts.getContactNumberToArray();
                    for (int i=0; i< ContactNumber.size(); i++)
                    {
                        //Toast.makeText(getApplicationContext(), "No Location Acquired",Toast.LENGTH_SHORT).show();
                        Log.i("MyTag", "No Adress Acquired");
                        SENDSMSWITHOUTADDRESS(ContactNumber.get(i), "http://maps.google.com/?q=" + LATIT + "," + LONGI);
                        SmsManager smsManager = SmsManager.getDefault();
                        //smsManager.sendTextMessage("+923422066670", null,"TESTING "+ address+ ", "+f4 + ", "+city + ", " + country, null, null);
                        //smsManager.sendTextMessage("+923422066670", null, "http://maps.google.com/?q="+LATIT+","+LONGI, null, null);
                        Log.i("MyTag", "Message Sent Without Address");
                    }
                }
                /*SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+923422066670", null,address, null, null);
                smsManager.sendTextMessage("+923422066670", null, "http://maps.google.com/?q="+LATIT+","+LONGI, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();*/

            }

        };

        List<String> interval = dbHandlerInterval.getIntervalToArray();
        Toast.makeText(this,"interval.size()",Toast.LENGTH_LONG);
        Log.i("INTERVAL", String.valueOf(interval.size()));
        System.out.println(interval.size());

        int intervalint = Integer.parseInt(interval.get(0));

        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, intervalint* 60000, 0, locListener);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,intervalint* 60000, 0, locListener);
    }

    public void SENDSMSWITHADDRESS(String num, String text){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, text, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent With Address",Toast.LENGTH_SHORT).show();
    }

    public void SENDSMSWITHOUTADDRESS(String num, String text){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, text, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent Without Address",Toast.LENGTH_SHORT).show();
    }

/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
        Log.i("Button","Volume down pressed");
        }
        return true;
    }*/


}

