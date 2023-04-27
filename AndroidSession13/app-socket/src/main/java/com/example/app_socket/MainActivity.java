package com.example.app_socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import com.example.app_socket.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.openSocketServer.setOnClickListener(this);
        mainBinding.connectionSocketServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_socket_server:
                openSocketServer();
                break;
            case R.id.connection_socket_server:
                connectionSocketServer();
                break;
            default:
                break;
        }
    }

    private void openSocketServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket server = null;
                try {
                    server = new ServerSocket(8848);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    Socket socket = null;
                    OutputStream os = null;
                    try {
                        socket = server.accept();
                        os = socket.getOutputStream();
                        os.write("已连接至服务器！".getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void connectionSocketServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                BufferedReader reader = null;
                while (true) {
                    try {
                        socket = new Socket("127.0.0.1", 8848);
                        InputStream is = socket.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line + "\n");
                        }
                        showResponse(response.toString());
                        Thread.sleep(500);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainBinding.responseText.append(response);
            }
        });
    }
}