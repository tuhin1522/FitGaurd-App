package com.tuhin.fitgaurdapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    Button loginbutton;

    private EditText editTextName, editTextGender, editTextDate, editTextHeight, editTextWeight;

    public static String NAME, GENDER;
    public static String DATE;
    public static int WEIGHT;
    public static double HEIGHT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loginbutton = findViewById(R.id.loginbutton);
        editTextGender = findViewById(R.id.editTextGender);
        editTextDate = findViewById(R.id.editTextDate);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextName = findViewById(R.id.editTextName);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextGender.getText().toString().trim().isEmpty() ||
                        editTextDate.getText().toString().trim().isEmpty() ||
                        editTextHeight.getText().toString().trim().isEmpty() ||
                        editTextWeight.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please Fill the Login form", Toast.LENGTH_SHORT).show();
                } else {


                    NAME = editTextName.getText().toString();
                    GENDER = editTextGender.getText().toString();
                    DATE = editTextDate.getText().toString();
                    HEIGHT = Double.parseDouble(editTextHeight.getText().toString());
                    WEIGHT = Integer.parseInt(editTextWeight.getText().toString());

                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("flag", true);
                    editor.apply();

//                    Profile profileFragment = new Profile();
//                    //profileFragment.setArguments(bundle);
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, profileFragment);
//                    fragmentTransaction.commit();

                    Intent iHome = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(iHome);
                }
            }
        });
    }
}
