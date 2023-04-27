package com.example.androidsession_08.banner;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsession_08.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initBanner();
    }

    public void initBanner() {
        banner = findViewById(R.id.banner);
        int[] imageResourceId = new int[]{R.mipmap.banner_1, R.mipmap.banner_2, R.mipmap.banner_3, R.mipmap.banner_4, R.mipmap.banner_5};
        List<Integer> imageList = new ArrayList<>();

        for (int i = 0; i < imageResourceId.length; i++) {
            imageList.add(imageResourceId[i]);
        }

        banner.setAdapter(new BannerImageAdapter(imageList) {
            @Override
            public void onBindView(Object holder, Object data, int position, int size) {
                BannerImageHolder imageHolder = (BannerImageHolder) holder;
                Glide.with(imageHolder.imageView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(imageHolder.imageView);
            }
        }).setIndicator(new CircleIndicator(getApplicationContext())).setLoopTime(3000);
    }
}
