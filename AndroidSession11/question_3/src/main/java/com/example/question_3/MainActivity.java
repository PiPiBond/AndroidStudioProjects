package com.example.question_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.question_3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IMyAidlInterface aidlInterface;
    ActivityMainBinding mainBinding;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.d("MainActivity[onServiceConnected]", "This triangle is a " + aidlInterface.getTriangle());
                aidlInterface.getTriangle();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity[onServiceConnected]", "onServiceDisconnected executed");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.startAidlBtn.setOnClickListener(this);
        mainBinding.stopAidlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.start_aidl_btn:
                intent = new Intent(this, MyService.class);
                bindService(intent, connection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.stop_aidl_btn:
                unbindService(connection);
                break;
            default:
                break;
        }
    }
}