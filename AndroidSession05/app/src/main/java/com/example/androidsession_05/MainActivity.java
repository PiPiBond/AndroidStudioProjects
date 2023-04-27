package com.example.androidsession_05;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidsession_05.databinding.ActivityMainBinding;
import com.example.androidsession_05.tabs.ChatFragment;
import com.example.androidsession_05.tabs.ContactFragment;
import com.example.androidsession_05.tabs.DiscoverFragment;
import com.example.androidsession_05.tabs.MeFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        // 取消标题栏
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // 默认微信页面
        replaceFragment(new ChatFragment());
        // 监听标签
        RadioGroup tabs = findViewById(R.id.tabGroup);
        tabs.setOnCheckedChangeListener(this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contentFrag, fragment);
        transaction.commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.chats:
                replaceFragment(new ChatFragment());
                break;
            case R.id.contacts:
                replaceFragment(new ContactFragment());
                break;
            case R.id.discover:
                replaceFragment(new DiscoverFragment());
                break;
            case R.id.me:
                replaceFragment(new MeFragment());
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}