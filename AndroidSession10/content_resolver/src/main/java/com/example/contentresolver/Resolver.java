package com.example.contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contentresolver.databinding.ActivityResolverBinding;

public class Resolver extends AppCompatActivity implements View.OnClickListener {
    Uri uri = Uri.parse("content://com.example.contentprovider.Provider/");
    ActivityResolverBinding resolverBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolverBinding = ActivityResolverBinding.inflate(getLayoutInflater());
        setContentView(resolverBinding.getRoot());

        resolverBinding.queryButton.setOnClickListener(this);
        resolverBinding.insertButton.setOnClickListener(this);
        resolverBinding.deleteButton.setOnClickListener(this);
        resolverBinding.updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContentValues values;
        switch (v.getId()) {
            case R.id.query_button:
                getContentResolver().query(uri, null, "selection", null, null);

                Log.i("Resolver-query", "query()");
                break;
            case R.id.insert_button:
                values = new ContentValues();
                values.put("name", "张三");
                values.put("id", "210111302");
                getContentResolver().insert(uri, values);

                Log.i("Resolver-insert", "insert()");
                break;
            case R.id.delete_button:
                getContentResolver().delete(uri, "where", null);

                Log.i("Resolver-delete", "delete()");
                break;
            case R.id.update_button:
                values = new ContentValues();
                values.put("name", "罗文泽");
                values.put("id", "210111302");
                getContentResolver().update(uri, values, "where", null);

                Log.i("Resolver-update", "update()");
                break;
            default:
                break;
        }

    }
}