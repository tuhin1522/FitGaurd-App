package com.tuhin.fitgaurdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BurnActivity extends AppCompatActivity {

    Button burnbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burn);

        burnbutton = findViewById(R.id.burnbutton);
        burnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent burnIntent = new Intent(BurnActivity.this, WaterActivity.class);
                startActivity(burnIntent);
                finish();
            }
        });
    }
}