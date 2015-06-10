package com.example.lijing.coolwwath.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lijing on 15/6/10.
 */
public class AutoUpdateRecevier extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,AutoUpdateService.class);
        context.startService(i);
    }
}
