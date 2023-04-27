package com.example.androidsession_08.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsession_08.R;
import com.example.androidsession_08.databinding.ActivityLaunchModeBinding;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCollector collector = ActivityCollector.getActivityCollector();

    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLaunchModeBinding launchModeBinding = ActivityLaunchModeBinding.inflate(getLayoutInflater());
        setContentView(launchModeBinding.getRoot());

        layout = launchModeBinding.modesLayout;
        Button button = new Button(this);
        button.setText("关闭所有测试 Activity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collector.destroyAll();
            }
        });
        layout.addView(button);

        int taskId = getTaskId();
        String simpleName = getClass().getSimpleName();
        TextView textView = new TextView(this);
        textView.setText("TaskId: " + taskId + "\n" + "SimpleName: " + simpleName);
        layout.addView(textView);

        collector.addActivity(this);

        launchModeBinding.standardButton.setOnClickListener(this);
        launchModeBinding.singleTopButton.setOnClickListener(this);
        launchModeBinding.singleTaskButton.setOnClickListener(this);
        launchModeBinding.singleInstanceButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        collector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.standard_button:
                intent = new Intent(this, StandardModeActivity.class);
                startActivity(intent);
                break;
            case R.id.single_top_button:
                intent = new Intent(this, SingleTopModeActivity.class);
                startActivity(intent);
                break;
            case R.id.single_task_button:
                intent = new Intent(this, SingleTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.single_instance_button:
                intent = new Intent(this, SingleInstanceActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
