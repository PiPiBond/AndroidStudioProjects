package com.example.content_observer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.content_observer.databinding.ActivitySmsobserverBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSObserver extends AppCompatActivity {
    ActivitySmsobserverBinding smsObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsObserver = ActivitySmsobserverBinding.inflate(getLayoutInflater());
        setContentView(smsObserver.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContentResolver().registerContentObserver(Telephony.Sms.CONTENT_URI, true, new Observer(new Handler()));
                Log.i("权限申请", "onRequestPermissionsResult:授权成功");
            } else {
                Log.i("权限申请", "onRequestPermissionsResult:授权失败");
            }
        }
    }

    private class Observer extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public Observer(Handler handler) {
            super(handler);
        }

        @SuppressLint("Range")
        @Override
        public void onChange(boolean selfChange) {
            Cursor cursor = getContentResolver().query(Telephony.Sms.Sent.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                if (Math.abs(System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndex("date"))) < 5000) {
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String subject = cursor.getString(cursor.getColumnIndex("subject"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));

                    SimpleDateFormat s3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    date = s3.format(Long.parseLong(date));

                    Log.d("号码", address);
                    if (subject != null) {
                        Log.d("主题", subject);
                    }
                    Log.d("内容", body);
                    Log.d("时间", date);
                }
            }
            cursor.close();
        }
    }
}