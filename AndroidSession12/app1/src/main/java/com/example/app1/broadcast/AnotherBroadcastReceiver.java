package com.example.app1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AnotherBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AnotherBroadcastReceiver:\n" + intent.getStringExtra("name") + ":" + intent.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        Log.i("AnotherBroadcastReceiver", intent.getStringExtra("name") + "-" + intent.getStringExtra("id"));
        abortBroadcast();
    }
}