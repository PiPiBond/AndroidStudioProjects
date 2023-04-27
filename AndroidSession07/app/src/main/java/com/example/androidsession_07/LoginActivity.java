package com.example.androidsession_07;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidsession_07.databinding.ActivityLoginBinding;
import com.example.androidsession_07.databinding.DataProgressBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding loginBinding;
    Integer hasRead = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_custom);

        loginBinding.loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            EditText name = findViewById(R.id.editText1);
            EditText password = findViewById(R.id.editText2);
            if (!isEmptyEditText(name) && !isEmptyEditText(password)) {
                initMainActivity();
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "请输入用户名/密码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initMainActivity() {
        DataProgressBinding progressBinding = DataProgressBinding.inflate(getLayoutInflater());
        ProgressBar progressBar = progressBinding.getRoot().findViewById(R.id.circle_progress);
        TextView textView = progressBinding.getRoot().findViewById(R.id.text_progress);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(progressBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();
        LoginAsyncTask asyncTask = new LoginAsyncTask(progressBinding.getRoot().getContext(), textView, progressBar, dialog);
        asyncTask.execute();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private boolean isEmptyEditText(EditText editText) {
        String s = editText.getText().toString().trim();
        return s.matches("");
    }


    private class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        Context context;
        TextView textView;
        ProgressBar progressBar;
        AlertDialog dialog;

        public LoginAsyncTask(Context context, TextView textView, ProgressBar progressBar, AlertDialog dialog) {
            this.context = context;
            this.textView = textView;
            this.progressBar = progressBar;
            this.dialog = dialog;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            hasRead = 0;
            for (int i = 0; i < 10000; i++) {
                stringBuilder.append((i + 1) + "\n");
                hasRead++;
                publishProgress(hasRead);
            }
            dialog.dismiss();
            return stringBuilder.toString();
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setMax(10000);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("正在加载好友信息 " + values[0] / 100 + "%");
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }
}