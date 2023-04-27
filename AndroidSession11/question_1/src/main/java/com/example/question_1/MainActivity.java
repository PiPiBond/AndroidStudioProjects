package com.example.question_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.question_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mainBinding;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection", "onServiceConnected executed");
                MyService.MyBinder binder = (MyService.MyBinder) service;
                binder.fun();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("ServiceConnection", "onServiceDisconnected executed");
            }
        };
        mainBinding.startServiceBtn.setOnClickListener(this);
        mainBinding.stopServiceBtn.setOnClickListener(this);
        mainBinding.bindServiceBtn.setOnClickListener(this);
        mainBinding.unbindServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.start_service_btn:
                intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
                break;
            case R.id.stop_service_btn:
                intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);
                break;
            case R.id.bind_service_btn:
                intent = new Intent(MainActivity.this, MyService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service_btn:
                unbindService(connection);
                break;
            default:
                break;
        }
    }
}