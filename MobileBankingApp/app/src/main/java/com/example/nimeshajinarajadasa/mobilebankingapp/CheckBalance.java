package com.example.nimeshajinarajadasa.mobilebankingapp;

/**
 * Created by Nimesha Jinarajadasa on 6/27/2015.
 */
//public class checkBalance {
//}

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBalance extends Activity {
    Button btnSendSMS;
    EditText etAcnumber;
    EditText SMSes;

    IntentFilter intentFilter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkbalance);

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        btnSendSMS = (Button) findViewById(R.id.smsbtn);
        etAcnumber = (EditText) findViewById(R.id.acNo);
        SMSes = (EditText)findViewById(R.id.blanaceet);


        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sendSMS("0719752639","cb,"+etAcnumber.getText().toString());
                Toast.makeText(v.getContext(),"Wait...",Toast.LENGTH_LONG).show();

            }


        });

    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---

            SMSes.setText(intent.getExtras().getString("sms"), TextView.BufferType.EDITABLE);
        }
    };
    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);

    }

    @Override
    protected void onResume() {
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }
    @Override
    protected void onPause() {
        //---unregister the receiver---
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}

