package com.example.nimeshajinarajadasa.mobilebankingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nimesha Jinarajadasa on 9/5/2015.
 */
public class PayActivity extends Activity {

    private RadioGroup radioBillGroup;
    private RadioButton radioBillButton;
    private Button btnDisplay;
    IntentFilter intentFilter;

    private RadioGroup radioBillGroup_pay;
    private RadioButton radioBillButton_pay;
    private Button btnDisplay_pay;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay);

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        final EditText myAcNo = (EditText)findViewById(R.id.ac_no);

        radioBillGroup = (RadioGroup) findViewById(R.id.bill_type);

        btnDisplay = (Button) findViewById(R.id.bill_select_btn);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioBillGroup.getCheckedRadioButtonId();
                radioBillButton=(RadioButton)findViewById(selectedId);
                String accountNum = myAcNo.getText().toString();
                Toast.makeText(PayActivity.this,radioBillButton.getText(),Toast.LENGTH_SHORT).show();

                try {

                    final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS AccountTable (Acid INTEGER AUTO INCREMENT PRIMARYKEY, Acname VARCHAR(30), Acnum VARCHAR(30));");
                    db.execSQL("INSERT INTO AccountTable(Acname,Acnum) VALUES('" + radioBillButton.getText().toString() + "','" + accountNum + "');");


                    db.close();
                } catch (NullPointerException ex) {
                }

                Toast.makeText(getApplicationContext(), "Account Number Added!!",
                        Toast.LENGTH_LONG).show();

            }
        });




        final EditText myAcNo_pay = (EditText)findViewById(R.id.ac_no_payment);

        radioBillGroup_pay = (RadioGroup) findViewById(R.id.bill_type_payment);

        btnDisplay_pay = (Button) findViewById(R.id.bill_select_btn_payment);
        final EditText amount = (EditText)findViewById(R.id.amount_payment);

        btnDisplay_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioBillGroup_pay.getCheckedRadioButtonId();
                radioBillButton_pay=(RadioButton)findViewById(selectedId);
                String accountNum = myAcNo.getText().toString();
                Toast.makeText(PayActivity.this,radioBillButton_pay.getText(),Toast.LENGTH_SHORT).show();

                try {

                    final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                    Cursor cur = db.rawQuery("SELECT Acnum FROM AccountTable WHERE Acname='" + radioBillButton_pay.getText().toString() + "'", null);
                    cur.moveToFirst();

                    if(!cur.moveToFirst()){
                        Toast.makeText(getApplicationContext(), "First add correct account number to prefered bill type!",
                                Toast.LENGTH_LONG).show();
                    }else {
                        String RecieverNumRetrieve = cur.getString(cur.getColumnIndex("Acnum"));

                        sendSMS("0719752639", "pay," + myAcNo_pay.getText().toString() + ":" + RecieverNumRetrieve.toString() + ":" + amount.getText().toString());
                        Toast.makeText(PayActivity.this, "Wait for Payment!...", Toast.LENGTH_LONG).show();





                    }
                    db.close();

                } catch (NullPointerException ex) {
                }

                Toast.makeText(getApplicationContext(), "Payment Done!!",
                        Toast.LENGTH_LONG).show();

            }
        });


    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---

            Toast.makeText(PayActivity.this,intent.getExtras().getString("sms"),Toast.LENGTH_SHORT).show();




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






