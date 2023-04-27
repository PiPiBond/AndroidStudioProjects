package com.example.androidsession_08.launch_mode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidsession_08.R;
import com.example.androidsession_08.databinding.ActivityLaunchModeBinding;

public class LaunchModeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLaunchModeBinding launchModeBinding = ActivityLaunchModeBinding.inflate(getLayoutInflater());
        setContentView(launchModeBinding.getRoot());

        launchModeBinding.standardButton.setOnClickListener(this);
        launchModeBinding.singleTopButton.setOnClickListener(this);
        launchModeBinding.singleTaskButton.setOnClickListener(this);
        launchModeBinding.singleInstanceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.standard_button:
                intent = new Intent(LaunchModeActivity.this, StandardModeActivity.class);
                startActivity(intent);
                break;
            case R.id.single_top_button:
                intent = new Intent(LaunchModeActivity.this, SingleTopModeActivity.class);
                startActivity(intent);
                break;
            case R.id.single_task_button:
                intent = new Intent(LaunchModeActivity.this, SingleTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.single_instance_button:
                intent = new Intent(LaunchModeActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}