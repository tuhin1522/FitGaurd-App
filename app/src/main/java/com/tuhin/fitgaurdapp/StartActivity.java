package com.tuhin.fitgaurdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button startbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        startbutton = findViewById(R.id.startbutton);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                Boolean check=pref.getBoolean("flag",false);

                Intent iNext;
                if(check){
                    iNext = new Intent(StartActivity.this,MainActivity.class);
                }
                else{
                    iNext = new Intent(StartActivity.this,GoalActivity.class);
                }

                startActivity(iNext);
                finish();
            }
        }, 1000);
    }
}