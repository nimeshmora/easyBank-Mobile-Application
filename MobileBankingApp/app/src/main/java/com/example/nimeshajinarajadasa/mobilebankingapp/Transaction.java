package com.example.nimeshajinarajadasa.mobilebankingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nimesha Jinarajadasa on 6/29/2015.
 */
public class Transaction extends Activity {


    TextView myMsg;
    AlertDialog.Builder builder;
    IntentFilter intentFilter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");


        Button okTransactbtn = (Button)findViewById(R.id.sendtrans);
        final EditText reciveAccountNumber = (EditText)findViewById(R.id.receiveAc);
        final EditText moneyAmount = (EditText)findViewById(R.id.amount);
        final EditText senderAccountNumber = (EditText)findViewById(R.id.senderAc);

        okTransactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS("0719752639","tr,"+senderAccountNumber.getText().toString()+":"+reciveAccountNumber.getText().toString()+":"+moneyAmount.getText().toString());
                Toast.makeText(view.getContext(),"Wait for Transaction...",Toast.LENGTH_LONG).show();
            }
        });





    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---
            builder = new AlertDialog.Builder(context);

            // Creates textview for centre title
            myMsg = new TextView(context);
            myMsg.setText("Bank Message!");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(35);
            myMsg.setTextColor(Color.BLACK);


            builder.setCustomTitle(myMsg);

            builder.setMessage(intent.getExtras().getString("sms"));
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.show();
        }
    };

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
