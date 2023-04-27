package com.example.androidsession_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.androidsession_04.databinding.ActivityMainBinding;
import com.example.androidsession_04.list_view.Fruit;
import com.example.androidsession_04.list_view.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        initFruits();

        FruitAdapter adapter = new FruitAdapter(this, R.layout.fruit_item, fruitList);
        mainBinding.listView.setAdapter(adapter);
        mainBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast toast = Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT);
                toast.show();

                if (fruit.getName() == "Apple") {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ProgressActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

    }

    private void initFruits() {
        for (int i = 0; i < 3; i++) {
            fruitList.add(new Fruit("Apple", R.drawable.apple_pic));
            fruitList.add(new Fruit("Banana", R.drawable.banana_pic));
            fruitList.add(new Fruit("Orange", R.drawable.orange_pic));
            fruitList.add(new Fruit("Watermelon", R.drawable.watermelon_pic));
            fruitList.add(new Fruit("Pear", R.drawable.pear_pic));
            fruitList.add(new Fruit("Grape", R.drawable.grape_pic));
            fruitList.add(new Fruit("Pineapple", R.drawable.pineapple_pic));
            fruitList.add(new Fruit("Strawberry", R.drawable.strawberry_pic));
            fruitList.add(new Fruit("Cherry", R.drawable.cherry_pic));
            fruitList.add(new Fruit("Mango", R.drawable.mango_pic));
        }
    }
}