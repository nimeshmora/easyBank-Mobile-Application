package com.example.nimeshajinarajadasa.mobilebankingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Nimesha Jinarajadasa on 6/30/2015.
 */
public class Splash extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(8*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }
}
