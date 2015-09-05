package com.example.nimeshajinarajadasa.mobilebankingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nimesha Jinarajadasa on 9/3/2015.
 */
public class LoginActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        Button logbtn = (Button)findViewById(R.id.login_btn);
        Button registerbtn = (Button)findViewById(R.id.register_btn);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText lgname = (EditText)findViewById(R.id.u_name);
                final EditText lgpass = (EditText)findViewById(R.id.password);

                String loginname = lgname.getText().toString();
                String loginpass = lgpass.getText().toString();


                if(loginpass.isEmpty()||loginname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Username And Password !",
                            Toast.LENGTH_LONG).show();
                }else{

                    try {
                        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);

                        Cursor cur = db.rawQuery("SELECT Password FROM UserTable WHERE Uname='" + loginname + "'", null);


                        cur.moveToFirst();

                        if(!cur.moveToFirst()){
                            Toast.makeText(getApplicationContext(), "Enter Correct Username!",
                                    Toast.LENGTH_LONG).show();
                        }




                        else {
                            String passwordRetrieve = cur.getString(cur.getColumnIndex("Password"));

                            if (passwordRetrieve.equals(loginpass)) {
                                Intent mainmenupage = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainmenupage);
                            } else {
                                Toast.makeText(getApplicationContext(), "Enter Correct Password!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        cur.close();
                        db.close();
                    }catch (NullPointerException ex){

                    }

                }


            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerpage = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(registerpage);



            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}





















