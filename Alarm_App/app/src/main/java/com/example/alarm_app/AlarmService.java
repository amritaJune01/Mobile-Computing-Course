package com.example.alarm_app;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmService extends Service {

    MediaPlayer player;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
    private ServiceHandler mServiceHandler;
    private boolean bat_low = false, bat_ok = false, act_pow_con = false, phn_state = false, serviceStopped = false,serviceStarted = false;
    private int num_of_broadcast = 0;
    static String selectedTime1 = null, selectedTime2 = null;

    public AlarmService() {
    }

    private void startAlarm() {
        player = MediaPlayer.create(getBaseContext(), Settings.System.DEFAULT_RINGTONE_URI);
        player.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (player != null) player.stop();
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                while (!serviceStopped) {

                    if (selectedTime1 != null && simpleDateFormat.parse(selectedTime1) != null && 0 == (simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime())).getTime() - simpleDateFormat.parse(selectedTime1).getTime())) {

                        Toast.makeText(getBaseContext(), "Alarm 1 is ringing", Toast.LENGTH_SHORT).show();
                        Log.i(String.valueOf(AlarmService.class), "Alarm 1 is ringing");

                        startAlarm();

                        if (selectedTime2 != null && simpleDateFormat.parse(selectedTime1).equals(simpleDateFormat.parse(selectedTime2)))
                            selectedTime2 = null;
                        selectedTime1 = null;

                    } else if (selectedTime2 != null && simpleDateFormat.parse(selectedTime2) != null && 0 == (simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime())).getTime() - simpleDateFormat.parse(selectedTime2).getTime())) {

                        Toast.makeText(getBaseContext(), "Alarm 2 is ringing", Toast.LENGTH_SHORT).show();
                        Log.i(String.valueOf(AlarmService.class), "Alarm 2 is ringing");

                        startAlarm();

                        selectedTime2 = null;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!serviceStarted){
            serviceStarted = true;
            Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(AlarmService.class), "Service started");

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = startId;
            mServiceHandler.sendMessage(msg);
        }else{
            Toast.makeText(this, "Service has already started", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(AlarmService.class), "Service has already started started");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceStopped = true;
        serviceStarted = false;
        if (player != null) player.stop();
        unregisterReceiver(broadcastReceiver);
        Toast.makeText(getBaseContext(), "Service stopped", Toast.LENGTH_SHORT).show();
        Log.i(String.valueOf(AlarmService.class), "Service stopped");
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
                if (!bat_low) {
                    bat_low = true;
                    num_of_broadcast += 1;
                }
            }
            if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
                if (!bat_ok) {
                    bat_ok = true;
                    num_of_broadcast += 1;
                }
            }
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                if (!act_pow_con) {
                    act_pow_con = true;
                    num_of_broadcast += 1;
                }
            }
            if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                String temp = intent.getStringExtra((TelephonyManager.EXTRA_STATE));
                if (temp.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    phn_state = true;
                    num_of_broadcast += 1;
                }
            }

            if (num_of_broadcast >= 1) {
                Toast.makeText(getBaseContext(), "Service stopped", Toast.LENGTH_SHORT).show();
                Log.i(String.valueOf(AlarmService.class), "Service stopped");
                stopSelf();
            }
        }

    };
}