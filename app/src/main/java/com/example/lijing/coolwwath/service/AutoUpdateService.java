package com.example.lijing.coolwwath.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.lijing.coolwwath.util.HttpCallbackListener;
import com.example.lijing.coolwwath.util.HttpUtil;
import com.example.lijing.coolwwath.util.Utility;

/**
 * Created by lijing on 15/6/10.
 */
public class AutoUpdateService extends Service{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour =  8 * 60 * 60 * 1000;
//        int anHour =  5000;
        Long triggerAlarm = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(AutoUpdateService.this,AutoUpdateRecevier.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAlarm,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code","");
        if(weatherCode!=null){
            String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
            HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Utility.handleWeatherResponse(AutoUpdateService.this,response);
                    Log.e("Server",response+"");
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
