package com.example.androidsession_08.life_cycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidsession_08.R;
import com.example.androidsession_08.databinding.ActivityLifeCycleBinding;

public class LifeCycleActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLifeCycleBinding lifeCycleBinding = ActivityLifeCycleBinding.inflate(getLayoutInflater());
        setContentView(lifeCycleBinding.getRoot());

        lifeCycleBinding.normalActivityButton.setOnClickListener(this);
        lifeCycleBinding.dialogActivityButton.setOnClickListener(this);

        layout = lifeCycleBinding.activityCycleLayout;
        TextView textView = new TextView(this);
        textView.setText("Activity 被创建(onCreate)");
        layout.addView(textView);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.normal_activity_button:
                intent = new Intent(LifeCycleActivity.this, CustomActivity.class);
                startActivity(intent);
                break;
            case R.id.dialog_activity_button:
                intent = new Intent(LifeCycleActivity.this, CustomDialogActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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
        Toast.makeText(LifeCycleActivity.this, "LifeCycleActivity 不再可见", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(LifeCycleActivity.this, "LifeCycleActivity 被销毁", Toast.LENGTH_SHORT).show();
    }
}