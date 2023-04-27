package com.example.androidsession_09;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.androidsession_09.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ActivityMainBinding mainBinding;
    SQLiteDatabase db;

    private ListView listView;
    private List<Map<String, Object>> friendMapList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        // 传入较大数字时会执行更新数据库的方法 onUpgrade()
        DatabaseHelper databaseHelper = new DatabaseHelper(this, "friend.db", null, 1);
        db = databaseHelper.getWritableDatabase();

        // 从数据库读取 friend 消息
        loadFriendsData();
        // 监听
        mainBinding.friendListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView friendId = view.findViewById(R.id.friend_id);
        Integer fId = Integer.valueOf(friendId.getText().toString());

        TextView friendName = view.findViewById(R.id.nick_name);
        String name = String.valueOf(friendName.getText());

        TextView msg = view.findViewById(R.id.msg);
        String message = String.valueOf(msg.getText());

        showPopupMenu(view, fId, name,message, position);
    }

    private void showPopupMenu(View v, Integer fId, String name,String msg, int index) {
        //定义PopupMenu对象
        PopupMenu popupMenu = new PopupMenu(this, v);
        //设置PopupMenu对象的布局
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        //设置PopupMenu的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        friendMapList.remove(index);
                        adapter.notifyDataSetChanged();
                        db.delete("messages", "id = ?", new String[]{fId.toString()});
                        break;
                    case R.id.upgrade:
                        dataUpgrade(fId, name,msg,index);
                        break;
                }

                return true;
            }
        });
        //显示菜单
        popupMenu.show();
    }


    private void dataUpgrade(Integer id, String name,String msg,int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改备注");
        LinearLayout layout = new LinearLayout(this);
        EditText editText = new EditText(this);
        editText.setText(name);
        layout.addView(editText);

        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = editText.getText().toString();
                Map<String, Object> contentMap = new HashMap<>();
                contentMap.put("nick_name",s);
                contentMap.put("msg",msg);
                friendMapList.set(index,contentMap);
                adapter.notifyDataSetChanged();
                db.execSQL("update messages set friend_name = ? where id = ?", new Object[]{s, id});
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void loadFriendsData() {
        String[] mapKey = new String[]{"friend_id", "nick_name", "msg"};
        int[] componentId = new int[]{R.id.friend_id, R.id.nick_name, R.id.msg};
        // 通过简单适配器调用数据
        adapter = new SimpleAdapter(MainActivity.this, getData(), R.layout.item_friend, mapKey, componentId);
        // 数据可视化
        listView = findViewById(R.id.friendListView);
        listView.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private List<Map<String, Object>> getData() {
        friendMapList = new ArrayList<>();
        Cursor cursor = db.query("messages", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, Object> contentMap = new HashMap<>();
                contentMap.put("friend_id", cursor.getString(cursor.getColumnIndex("id")));
                contentMap.put("nick_name", cursor.getString(cursor.getColumnIndex("friend_name")));
                contentMap.put("msg", cursor.getString(cursor.getColumnIndex("message")));
                friendMapList.add(contentMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return friendMapList;
    }
}