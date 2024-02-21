package com.tuhin.fitgaurdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Notification_Activity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        textView = findViewById(R.id.tv_message);
        //call the data which is passed by another intent
        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));
    }
}