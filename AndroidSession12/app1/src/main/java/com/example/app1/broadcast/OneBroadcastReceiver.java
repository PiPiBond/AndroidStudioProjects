package com.example.app1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OneBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BroadcastReceiver:\n"+intent.getStringExtra("name") + ":" + intent.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        Log.i("BroadcastReceiver",intent.getStringExtra("name") + "-" + intent.getStringExtra("id"));
    }
}