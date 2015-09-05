package com.example.nimeshajinarajadasa.mobilebankingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Nimesha Jinarajadasa on 5/17/2015.
 */
public class SignUpActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button okButton = (Button)findViewById(R.id.passchange_btn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameregis = (EditText)findViewById(R.id.reg_name);
                EditText passregis = (EditText)findViewById(R.id.reg_pass);

                final String regname = nameregis.getText().toString();
                final String regpass = passregis.getText().toString();

                if(regname.isEmpty()|| regpass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Correct Username!",
                            Toast.LENGTH_LONG).show();
                }else {
                    try {

                        final SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS UserTable (Uid INTEGER AUTO INCREMENT PRIMARYKEY, Uname VARCHAR(30), Password VARCHAR(20));");
                        db.execSQL("INSERT INTO UserTable(Uname,Password) VALUES('" + regname + "','" + regpass + "');");


                        db.close();
                    } catch (NullPointerException ex) {
                    }

                    Toast.makeText(getApplicationContext(), "Signup Succeeded!",
                    Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

                }
            }
        });



    }
}











