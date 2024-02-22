package com.tuhin.fitgaurdapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ServiceConfigurationError;

public class StepForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            Log.d("TAG","FOREGROUND SERVICE RUNNING....");
                            try{
                                Thread.sleep(2000);
                            }
                            catch (InterruptedException e){

                            }
                        }

                    }
                }
        ).start();
        final String ch_id ="foreground service";
        NotificationChannel channel = new NotificationChannel(ch_id,ch_id, NotificationManager.IMPORTANCE_LOW);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification =new Notification.Builder(this,ch_id)
                .setContentText("This is the title")
                .setContentTitle("this is the title");
        startForeground(1001,notification.build());

        return super.onStartCommand(intent, flags, startId);
    }
}
