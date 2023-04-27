package com.example.app_url_connection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.app_url_connection.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;
    Handler handler;


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

        mainBinding.getRequest.setOnClickListener(this);
        mainBinding.postRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_request:
                Thread threadGet = new Thread(){
                    @Override
                    public void run() {
                        String str = sendGet("http://192.168.0.240:8080/loginGet");
                        Message message = new Message();
                        message.what = 1;
                        message.obj = str;
                        handler.sendMessage(message);
                    }
                };
                threadGet.start();
                break;
            case R.id.post_request:
                Thread threadPost = new Thread() {
                    @Override
                    public void run() {
                        String str = sendPost("http://192.168.0.240:8080/loginPost", "name=aaa" + "&pwd=222");
                        Message message = new Message();
                        message.what = 1;
                        message.obj = str;
                        handler.sendMessage(message);
                    }
                };
                threadPost.start();
                break;
            default:
                break;
        }
    }
    public static String sendGet(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                Log.i("【sendGet】", "sendGet: key"+key+",value:"+map.get(key));
            }
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.i("【sendGet】", "GET请求异常");
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendPost(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.i("【sendPost】", "POST请求异常");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}