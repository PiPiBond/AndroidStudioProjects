package com.example.androidsession_04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.androidsession_04.databinding.ActivityProgressBinding;

public class ProgressActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProgressBinding progressBinding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(progressBinding.getRoot());
        ProgressThread progressThread = new ProgressThread();
        progressThread.start();
    }

    class ProgressThread extends Thread {
        ProgressBar progressBar = findViewById(R.id.progressBarHorizontal);
        @Override
        public void run() {
            while( progressStatus < 100 ){
                progressStatus += 1;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progressStatus);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}