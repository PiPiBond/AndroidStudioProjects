package com.example.androidsession_07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    View view;
    private List<Friend> friends = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initFriends();
    }

    private void loadFriendsData() {
        String[] mapKey = new String[]{"friendLogo", "nickName", "msg"};
        int[] componentId = new int[]{R.id.friendLogo, R.id.nickName, R.id.msg};
        // 通过简单适配器调用数据
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, getData(), R.layout.item_friend, mapKey, componentId);
        // 数据可视化
        listView = findViewById(R.id.friendListView);
        listView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        // 加载组件数据
        List<Map<String, Object>> friendMapList = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++) {
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("friendLogo", friends.get(i).getImageId());
            contentMap.put("nickName", friends.get(i).getNickName());
            contentMap.put("msg", friends.get(i).getMsg());
            friendMapList.add(contentMap);
        }
        return friendMapList;
    }

    private void initFriends() {
        for (int i = 0; i < 3; i++) {
            friends.add(new Friend(R.drawable.apple_pic, "苹果", "39.9元翅桶回归，+1元摇出辣味条"));
            friends.add(new Friend(R.drawable.banana_pic, "香蕉", "宝藏新品桶桶狂省,全鸡第二份19.9"));
            friends.add(new Friend(R.drawable.orange_pic, "橙子", "长假7天嗨不停,200万个红包等你拆!"));
            friends.add(new Friend(R.drawable.watermelon_pic, "西瓜", "2023研招统考正式明天报名开始，还有11个事项要注意"));
            friends.add(new Friend(R.drawable.pear_pic, "梨", "假期余额不足！小丑喊你快乐充值！"));
            friends.add(new Friend(R.drawable.grape_pic, "葡萄", "王者荣耀 | 买奕星新皮肤抽免单"));
            friends.add(new Friend(R.drawable.pineapple_pic, "菠萝", "@广大消费者，中消协邀您参与调查~"));
            friends.add(new Friend(R.drawable.strawberry_pic, "草莓", "四川昨日新增省内感染者58例；多地发布最新通告"));
            friends.add(new Friend(R.drawable.cherry_pic, "樱桃", "引香远行，寻香行传记鉴赏奉上！"));
            friends.add(new Friend(R.drawable.mango_pic, "芒果", "14年前他千里背疯母上大学感动千万人，如今他的故事更加催泪......"));
        }
        loadFriendsData();
    }
}