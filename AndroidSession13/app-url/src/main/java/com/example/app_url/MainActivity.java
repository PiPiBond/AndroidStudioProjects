package com.example.app_url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.app_url.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;
    Bitmap bitmap;
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
                    ImageView imageView = findViewById(R.id.img);
                    imageView.setImageBitmap(bitmap);
                }
            }
        };


        mainBinding.downloadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download_image) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        URL url = new URL("https://wallpapercave.com/wp/wp4829678.jpg");
                        showResponse(url);
                        url.openConnection();
                        is = url.openStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        handler.sendEmptyMessage(1);
                        is.close();
                        is = url.openStream();
                        os = openFileOutput("wallpaper.jpg", MODE_PRIVATE);

                        byte[] bytes = new byte[1024];
                        int read;
                        while ((read = is.read(bytes)) != -1) {
                            os.write(bytes, 0, read);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    private void showResponse(final URL url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String file = url.getFile();
                String host = url.getHost();
                int port = url.getPort();
                String protocol = url.getProtocol();
                String path = url.getPath();
                String query = url.getQuery();
                mainBinding.responseText.setText("资源名：" + file + "\n主机名：" + host + "\n端口号：" + port + "\n协议名：" + protocol + "\n路径：" + path + "\n字符串：" + query);
            }
        });
    }
}