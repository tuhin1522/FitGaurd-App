package com.tuhin.fitgaurdapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Profile extends Fragment {
    TextView name, dateOfBirth, gender, weight, height;
    LinearLayout btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.TextName);
        gender = view.findViewById(R.id.GenderText);
        dateOfBirth = view.findViewById(R.id.DateOfBirthText);
        weight = view.findViewById(R.id.WeightText);
        height = view.findViewById(R.id.HeightText);
        btnLogout = view.findViewById(R.id.btnLogout);


        name.setText(ProfileActivity.NAME);
        gender.setText(ProfileActivity.GENDER);
        dateOfBirth.setText(ProfileActivity.DATE);
        weight.setText(String.valueOf(ProfileActivity.WEIGHT));
        height.setText(String.valueOf(ProfileActivity.HEIGHT));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the login data from SharedPreferences
                SharedPreferences pref = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear(); // Clear all data
                editor.apply();

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                getActivity().finish();
                //getActivity().finishAffinity(); // Close the current activity after logout
            }
        });





        return view;
    }
}
