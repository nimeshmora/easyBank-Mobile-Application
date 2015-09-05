package com.example.nimeshajinarajadasa.mobilebankingapp;

/**
 * Created by Nimesha Jinarajadasa on 6/27/2015.
 */
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
public class SMSReceiver extends BroadcastReceiver

        {


@Override
public void onReceive(Context context, Intent intent)
        {



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
        str += "SMS from your Bank " + msgs[i].getOriginatingAddress();
        str += " :";
        str += msgs[i].getMessageBody().toString();
        str += "\n";

            if (msgs[i].getOriginatingAddress()!= null && msgs[i].getOriginatingAddress().equals("0719752639")) {
                // Process our sms...
                abortBroadcast();
            }
        }


        //---display the new SMS message---




           // Toast.makeText(context, str, Toast.LENGTH_LONG).show();


        //---send a broadcast intent to update the SMS received in the activity---
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("SMS_RECEIVED_ACTION");
        broadcastIntent.putExtra("sms", str);
        context.sendBroadcast(broadcastIntent);
        }
        }
        }
