package com.tuhin.fitgaurdapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Profile extends Fragment {
    TextView name, dateofbirth, gender, weight, height;
    Button btnlogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.editTextName);
        gender = view.findViewById(R.id.editTextGender);
        dateofbirth = view.findViewById(R.id.editTextDateOfBirth);
        weight = view.findViewById(R.id.editTextWeight);
        height = view.findViewById(R.id.editTextHeight);
        btnlogout = view.findViewById(R.id.btnLogout);


        return view;
    }
}