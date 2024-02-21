package com.tuhin.fitgaurdapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tuhin.fitgaurdapp.databinding.FragmentAlarmBinding;

import java.util.ArrayList;

public class Alarm extends Fragment {

    AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    RecyclerView recyclerView;
    FloatingActionButton addAlarm;
    ArrayList<NotificationModel> arrNotification = new ArrayList<>();

    private FragmentAlarmBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addAlarm = view.findViewById(R.id.addNotification);

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RemainderActivity.class);
                startActivity(intent);
            }
        });
        //Cursor To Load data From the database
        Cursor cursor = new DatabaseManager(getContext()).readallreminders();
        while (cursor.moveToNext()) {
            NotificationModel model = new NotificationModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            arrNotification.add(model);
        }

        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(getContext(), arrNotification);
        recyclerView.setAdapter(alarmRecyclerViewAdapter);

        return view;
    }
}





