package com.example.androidsession_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidsession_08.banner.BannerActivity;
import com.example.androidsession_08.databinding.ActivityMainBinding;
import com.example.androidsession_08.launch_mode.LaunchModeActivity;
import com.example.androidsession_08.life_cycle.LifeCycleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.lifeCycleButton.setOnClickListener(this);
        mainBinding.launchModeButton.setOnClickListener(this);
        mainBinding.slideshow.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.life_cycle_button:
                intent = new Intent(MainActivity.this, LifeCycleActivity.class);
                startActivity(intent);
                break;
            case R.id.launch_mode_button:
                intent = new Intent(MainActivity.this, LaunchModeActivity.class);
                startActivity(intent);
                break;
            case R.id.slideshow:
                intent = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}