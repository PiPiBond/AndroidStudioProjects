package com.example.androidsession_08.life_cycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidsession_08.databinding.ActivityCustomBinding;

public class CustomActivity extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomBinding customBinding = ActivityCustomBinding.inflate(getLayoutInflater());
        setContentView(customBinding.getRoot());

        layout = customBinding.customLayout;

        TextView textView = new TextView(this);
        textView.setText("Activity 被创建(onCreate)");
        layout.addView(textView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView textView = new TextView(this);
        textView.setText("Activity 被重新启动(onRestart)");
        layout.addView(textView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = new TextView(this);
        textView.setText("Activity 被启动(onStart)");
        layout.addView(textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = new TextView(this);
        textView.setText("Activity 正在运行(onResume)");
        layout.addView(textView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TextView textView = new TextView(this);
        textView.setText("正在启动或恢复另一个 Activity(onPause)");
        layout.addView(textView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(CustomActivity.this, "CustomActivity 不再可见", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(CustomActivity.this, "CustomActivity 被销毁", Toast.LENGTH_SHORT).show();
    }
}