package com.tuhin.fitgaurdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoalActivity extends AppCompatActivity {

    Button goalbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalbutton = findViewById(R.id.goalbutton);
        goalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent burnintent = new Intent(GoalActivity.this, BurnActivity.class);
                startActivity(burnintent);
                finish();
            }
        });
    }
}