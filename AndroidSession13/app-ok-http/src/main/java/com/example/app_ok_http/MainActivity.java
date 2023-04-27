package com.example.app_ok_http;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.app_ok_http.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;
    private Handler handler;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    mainBinding.textView.setText(msg.obj.toString());
                }
            }
        };
        mainBinding.okGet.setOnClickListener(this);
        mainBinding.okPost.setOnClickListener(this);

        final Map<String, List<Cookie>> cookieStore = new HashMap<>();
        okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies == null ? new ArrayList<Cookie>() : cookies;
            }
        }).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_get:
                okGet();
                break;
            case R.id.ok_post:
                okPost();
                break;
            default:
                break;
        }
    }

    private void okGet() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String url = "http://192.168.0.240:8080/okGet?name=111&pwd=111";
                Request request = new Request.Builder().url(url).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();//同步请求
                    Message message = new Message();
                    message.what = 1;
                    message.obj = response.body().string().trim();
                    handler.sendMessage(message);
                } catch (IOException e) {
                    Log.i("【okGet】", "get请求异常");
                }
            }
        };
        thread.start();
    }

    private void okPost() {
        final View login = getLayoutInflater().inflate(R.layout.login, null);
        new AlertDialog.Builder(MainActivity.this).setTitle("登录系统").setView(login).setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int w) {
                String name = ((EditText) login.findViewById(R.id.name)).getText().toString();
                String pwd = ((EditText) login.findViewById(R.id.password)).getText().toString();
                String url = "http://192.168.0.240:8080/okPost";
                FormBody body = new FormBody.Builder().add("name", name).add("pwd", pwd).build();
                Request request = new Request.Builder().url(url).post(body).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("【okPost】", "post请求异常");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = response.body().string();
                        handler.sendMessage(message);
                    }
                });
            }
        }).setNegativeButton("取消", null).show();
    }
}