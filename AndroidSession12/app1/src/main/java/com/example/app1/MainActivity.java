package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.app1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mainBinding;
    private IntentFilter intentFilter;
    private BatteryChangedReceiver batteryChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.normalBroadcastButton.setOnClickListener(this);
        mainBinding.orderedBroadcastButton.setOnClickListener(this);
        mainBinding.systemBroadcastButton.setOnClickListener(this);
        WebView webView = mainBinding.webView;
        webView.loadUrl("file:///android_asset/sign.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebViewJavaScriptObject(this), "object");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent("com.example.app1.BROADCAST");
        intent.putExtra("name", "罗文泽");
        intent.putExtra("id", "210111302");
        intent.setPackage(getPackageName());
        switch (v.getId()) {
            case R.id.normal_broadcast_button:
                sendBroadcast(intent);
                break;
            case R.id.ordered_broadcast_button:
                sendOrderedBroadcast(intent, null);
                break;
            case R.id.system_broadcast_button:
                batteryChangedReceiver = new BatteryChangedReceiver();
                intentFilter = new IntentFilter();
                intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
                registerReceiver(batteryChangedReceiver, intentFilter);
                break;
            default:
                break;
        }
    }

    class WebViewJavaScriptObject {
        private Context context;

        public WebViewJavaScriptObject(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void toastSuccess() {
            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void toastFail() {
            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    class BatteryChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            int level = extras.getInt("level");
            Toast.makeText(context, "当前电量：" + level, Toast.LENGTH_SHORT).show();
            Log.i("BatteryChangedReceiver", "当前电量：" + level);
        }
    }
}