package com.example.question_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.question_4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;
    private MediaPlayer player;
    private AudioManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        player= MediaPlayer.create(this,R.raw.audio);
        player.setLooping(true);
        manager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

        mainBinding.playBtn.setOnClickListener(this);
        mainBinding.stopBtn.setOnClickListener(this);
        mainBinding.upBtn.setOnClickListener(this);
        mainBinding.downBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_btn:
                player.start();
                break;
            case R.id.stop_btn:
                player.pause();
                break;
            case R.id.up_btn:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                break;
            case R.id.down_btn:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
                break;
            default:
                break;
        }
    }
}