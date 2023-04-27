package com.example.androidsession_06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidsession_06.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    // 主页面布局
    private ActivityMainBinding mainBinding;
    // 自定义列表
    private List<Fruit> fruits = new ArrayList<>();
    // 单选列表下标
    private int itemIndex = 0;
    // 单选列表数组
    final String[] countries = {"China", "America", "Russia", "Britain", "France"};
    // 简单列表数组
    final String[] contacts = {"红果果", "绿泡泡", "金龟子", "小咕咚", "阿狸", "哪吒",};
    // 多选列表数组
    String[] apps = {"支付宝", "微信", "京东", "拼多多", "淘宝", "哔哩哔哩", "百度贴吧", "微博"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_custom);

        mainBinding.infoButton.setOnClickListener(this);
        mainBinding.simpleListButton.setOnClickListener(this);
        mainBinding.singleListButton.setOnClickListener(this);
        mainBinding.checkboxListButton.setOnClickListener(this);
        mainBinding.customListButton.setOnClickListener(this);
        mainBinding.customDialogButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.background_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bg_day:
                mainBinding.mainActivity.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.bg_night:
                mainBinding.mainActivity.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_button:
                infoClick();
                break;
            case R.id.simple_list_button:
                simpleListClick();
                break;
            case R.id.single_list_button:
                singleListClick();
                break;
            case R.id.checkbox_list_button:
                checkboxClick();
                break;
            case R.id.custom_list_button:
                customListClick();
                break;
            case R.id.custom_dialog_button:
                customDialogClick();
                break;
            default:
                break;
        }
    }

    private void deleteApp(int index) {
        String[] newApps = new String[apps.length - 1];
        for (int i = 0; i < newApps.length; i++) {
            if (i < index) {
                newApps[i] = apps[i];
            } else {
                newApps[i] = apps[i + 1];
            }
        }
        apps = newApps;
    }

    private List<Map<String, Object>> getData() {
        // 加载组件数据
        List<Map<String, Object>> fruitMapList = new ArrayList<>();
        for (int i = 0; i < fruits.size(); i++) {
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("fruit_logo", fruits.get(i).getImageId());
            contentMap.put("fruit_name", fruits.get(i).getName());
            fruitMapList.add(contentMap);
        }
        return fruitMapList;
    }

    private boolean isEmptyEditText(EditText editText) {
        String s = editText.getText().toString().trim();
        return s.matches("");
    }

    private void initFruit() {
        for (int i = 0; i < 3; i++) {
            fruits.add(new Fruit(R.drawable.apple_pic, "苹果"));
            fruits.add(new Fruit(R.drawable.banana_pic, "香蕉"));
            fruits.add(new Fruit(R.drawable.orange_pic, "橙子"));
            fruits.add(new Fruit(R.drawable.watermelon_pic, "西瓜"));
            fruits.add(new Fruit(R.drawable.pear_pic, "梨"));
            fruits.add(new Fruit(R.drawable.grape_pic, "葡萄"));
            fruits.add(new Fruit(R.drawable.pineapple_pic, "菠萝"));
            fruits.add(new Fruit(R.drawable.strawberry_pic, "草莓"));
            fruits.add(new Fruit(R.drawable.cherry_pic, "樱桃"));
            fruits.add(new Fruit(R.drawable.mango_pic, "芒果"));
        }
    }

    private void infoClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("基本信息");
        builder.setMessage("罗文泽\n210111302");
        builder.setCancelable(true);
        builder.setPositiveButton("确定", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpleListClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("联系人");

        builder.setItems(contacts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, contacts[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void singleListClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("地区");

        builder.setSingleChoiceItems(countries, itemIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemIndex = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, countries[itemIndex], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkboxClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<Integer> appList = new ArrayList<>();
        builder.setTitle("删除应用");
        builder.setMultiChoiceItems(apps, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    appList.add(which);
                } else {
                    appList.remove(Integer.valueOf(which));
                }
            }
        });

        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < appList.size(); i++) {
                    deleteApp(appList.get(i));
                }
                Toast.makeText(MainActivity.this, appList.size() + "个应用已删除", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appList.clear();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void customListClick() {
        // 初始化水果
        initFruit();
        // 加载水果布局
        View view = getLayoutInflater().inflate(R.layout.fruit_list, null);
        // 初始化对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 匹配
        String[] mapKey = new String[]{"fruit_logo", "fruit_name"};
        int[] componentId = new int[]{R.id.fruit_logo, R.id.fruit_name};
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_fruit, mapKey, componentId);
        ListView listView = view.findViewById(R.id.fruit_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = view.findViewById(R.id.fruit_name);
                Toast.makeText(MainActivity.this, name.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        // 显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void customDialogClick() {
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        TimePicker timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        Button submitButton = view.findViewById(R.id.submit_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = view.findViewById(R.id.student_name);
                EditText id = view.findViewById(R.id.student_id);
                DatePicker datePicker = view.findViewById(R.id.date_picker);
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int dayOfMonth = datePicker.getDayOfMonth();
                int hour = timePicker.getHour();

                if (!isEmptyEditText(name) && !isEmptyEditText(id)) {
                    initProgress();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, name.getText() + "_" + id.getText() + "\n出生年月：" + year + "年" + month + "月" + dayOfMonth + "日" + hour + "时", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void initProgress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.data_progress, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        Thread thread = new ProgressThread(dialog);
        thread.start();
    }

    class ProgressThread extends Thread {
        private AlertDialog dialog;

        public ProgressThread(AlertDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void run() {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }
}