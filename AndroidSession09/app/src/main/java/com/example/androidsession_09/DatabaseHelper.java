package com.example.androidsession_09;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase db;

    private String createFriend = "create table messages ( " +
            "id integer primary key autoincrement, " +
            "friend_name text, " +
            "message text)";

    public DatabaseHelper(Context context, String name, CursorFactory factory, Integer version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(createFriend);
        init();
        Toast.makeText(context, "创建数据库成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void init() {
        insert("苹果", "39.9元翅桶回归，+1元摇出辣味条");
        insert("香蕉", "宝藏新品桶桶狂省,全鸡第二份19.9");
        insert("橙子", "长假7天嗨不停,200万个红包等你拆!");
        insert("西瓜", "2023研招统考正式明天报名开始，还有11个事项要注意");
        insert("梨", "假期余额不足！小丑喊你快乐充值！");
        insert("葡萄", "王者荣耀 | 买奕星新皮肤抽免单");
        insert("菠萝", "@广大消费者，中消协邀您参与调查~");
        insert("草莓", "四川昨日新增省内感染者58例；多地发布最新通告");
        insert("樱桃", "引香远行，寻香行传记鉴赏奉上！");
        insert("芒果", "14年前他千里背疯母上大学感动千万人，如今他的故事更加催泪......");
    }

    private void insert(String name, String message) {
        ContentValues values = new ContentValues();
        values.put("friend_name", name);
        values.put("message", message);
        db.insert("messages", null, values);
    }

}
