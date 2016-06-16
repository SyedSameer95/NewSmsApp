package com.example.syedsameerulhasan.newsmsapp;

/**
 * Created by Syed Sameer Ul Hasan on 08-Jun-16.
 */
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class SmsReceiver extends BroadcastReceiver {

    public String strmsg;
    public String Lat;
    public String Lon;
    DBHandlerPoints dbHandlerpoints;
    DBHandlerContacts dbHandlercontacts;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onReceive(Context context, Intent intent)
    {

        dbHandlerpoints = new DBHandlerPoints(context);
        dbHandlercontacts = new DBHandlerContacts(context);
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";




        if (bundle != null)
        {

            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :";
                strmsg = msgs[i].getMessageBody().toString();
                str += "";
            }

            //---display the new SMS message---
            //Toast.makeText(context, strmsg, Toast.LENGTH_SHORT).show();
            String sender = msgs[0].getOriginatingAddress();

/*            Intent in = new Intent("SmsMessage.intent").putExtra("get_msg", sender+":"+strmsg);
            context.sendBroadcast(in);*/

            List<String> ContactNumber = dbHandlercontacts.getContactNumberToArray();//new ArrayList<String>();
            System.out.println(ContactNumber.size());
            //ContactNumber.add("+923422066670");
            //String test = dbHandlercontacts.databaseToString();
            for (int i=0; i< ContactNumber.size(); i++)
            {
            if (PhoneNumberUtils.compare(ContactNumber.get(i),sender)) {
                //Toast.makeText(context, strmsg, Toast.LENGTH_SHORT).show();

/*                try {
                    URL url = new URL(strmsg);
                    Toast.makeText(context,url.getQuery(), Toast.LENGTH_SHORT).show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/

                try {



                    String InputString = sender;
                    SharedPreferences prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("string_id", InputString); //InputString: from the EditText
                    editor.commit();

                    Log.i("SP","Green Sent");

                    URL url = new URL(strmsg);

                    StringTokenizer st = new StringTokenizer(url.getQuery(), ",", false);
                    while (st.hasMoreElements()) {
                        String paramValueToken = st.nextElement().toString();
                        StringTokenizer stParamVal = new StringTokenizer(paramValueToken, "=", false);
                        int i1 = 0;
                        while (stParamVal.hasMoreElements()) {
                            String separatedToken = stParamVal.nextElement().toString();
                            if (i1 == 0) {
                                Lat = separatedToken;
                            } else {
                                Lon = separatedToken;
                            }

                            i1++;
                        }
                    }

                    Points point = new Points(Lat, Lon, sender);
                    dbHandlerpoints.addpoints(point);
                    Log.i("SP", "Point Added");



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            }
        }
    }
}




