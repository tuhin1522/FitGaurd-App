package com.tuhin.fitgaurdapp;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startForegroundService;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Home extends Fragment implements SensorEventListener {
    private TextView textViewStepCounter;
    private TextView textViewStepDetector;
    private TextView textViewDistance;
    private TextView textViewCalorie;
    private TextView textViewTime;
    private Button btnReset;
    private Button btnIsPause;
    private SensorManager sensorManager;
    private Sensor mStepCounter, mStepDetector;
    private boolean isCounterSensorPresent, isDetectorSensorPresent;
    private boolean isPause;
    private int initialStepCount = 0;
    private int stepCount = 0;
    private int stepDetect = 0;
    private float stepLengthInMeter= .5F;
    private float perStepCalorie = .5F;
    private long timePause = 0;
    //private long startTime;
    private int stepCountTarget= 8000;
    //private Handler timerHandler = new Handler();
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //foreground Intent
        Intent serviceIntent = new Intent(getActivity(), StepForegroundService.class);
        ContextCompat.startForegroundService(getActivity(), serviceIntent);
        foregroundServiceRunning();

        textViewStepCounter = view.findViewById(R.id.textViewStepCounter);
        textViewStepDetector = view.findViewById(R.id.textViewStepDetector);
        textViewDistance = view.findViewById(R.id.textViewDistance);
        textViewCalorie = view.findViewById(R.id.textViewCalorie);
        //textViewTime = view.findViewById(R.id.textViewTime);
        btnReset = view.findViewById(R.id.btnReset);
        btnIsPause = view.findViewById(R.id.btnIsPause);

        //startTime = System.currentTimeMillis();
        // Load initialStepCount from SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        initialStepCount = prefs.getInt("initialStepCount", 0);

        // Load stepDetect from SharedPreferences
        stepDetect = prefs.getInt("stepDetect", 0);

        textViewStepDetector.setText("Step Detector: " + stepDetect);

        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        } else {
            textViewStepCounter.setText("Counter sensor is not present");
            isCounterSensorPresent = false;
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorSensorPresent = true;
        } else {
            textViewStepDetector.setText("Detector sensor is not present");
            isCounterSensorPresent = false;
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset step detector value and update the display
                stepDetect = 0;
                textViewStepDetector.setText("Step Detector: " + stepDetect);
                textViewDistance.setText("0 km distance covered");
                textViewCalorie.setText("0 calorie burned");

                // Store the current step detector value
                SharedPreferences.Editor editor = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
                editor.putInt("stepDetect", stepDetect);
                editor.apply();
            }
        });

        btnIsPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the isPause flag
                isPause = !isPause;

                // Update button text based on the state
                btnIsPause.setText(isPause ? "Resume" : "Pause");

                if (isPause) {
                    // Unregister listener to pause step counting
                    if (isDetectorSensorPresent) {
                        sensorManager.unregisterListener(Home.this, mStepDetector);
                    }
                } else {
                    // Register listener to resume step counting
                    if (isDetectorSensorPresent) {
                        sensorManager.registerListener(Home.this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == mStepDetector && !isPause) {
            stepDetect = (int) (stepDetect + sensorEvent.values[0]);
            textViewStepDetector.setText("Step Detector: " + stepDetect);

            // Store the current step detector value
            SharedPreferences.Editor editor = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putInt("stepDetect", stepDetect);
            editor.apply();
        }
        float distanceInKm = stepDetect* stepLengthInMeter/1000;
        textViewDistance.setText(String.format(Locale.getDefault(), "%.2f km distance covered", distanceInKm));

        int calorie= (int) (stepDetect * perStepCalorie);
        textViewCalorie.setText(calorie +" calories burned");

    }
    public boolean foregroundServiceRunning() {
        ActivityManager activityManager = (ActivityManager) requireContext().getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (StepForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Implementation if needed
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCounterSensorPresent) {
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (isDetectorSensorPresent && !isPause) {
            sensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isCounterSensorPresent) {
            sensorManager.unregisterListener(this, mStepCounter);
        }
        if (isDetectorSensorPresent) {
            sensorManager.unregisterListener(this, mStepDetector);
        }
    }

}
