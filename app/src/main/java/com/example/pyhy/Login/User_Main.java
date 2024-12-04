package com.example.pyhy.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pyhy.R;
import com.example.pyhy.playPng.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class User_Main extends AppCompatActivity {

//    private ImageView avatar;//用户实际头像
//    private TextView username;//用户实际名称
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private List<String> imageUrls = new ArrayList<>(); // 存储图片URL的列表
    private  List<Integer> imageResourceIds = new ArrayList<>();// 访问本地图片


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        Log.d("denglu","登入成功");
//        avatar = findViewById(R.id.avatar);
//        username = findViewById(R.id.username);
        viewPager2 = findViewById(R.id.slider_recycler);

        // 添加图片URL或资源
        imageResourceIds.add(R.drawable.p_1);
        imageResourceIds.add(R.drawable.p_2);
        imageResourceIds.add(R.drawable.p_3);


        // 创建适配器并设置(本地)
        viewPagerAdapter = new ViewPagerAdapter<>(this,imageResourceIds,false);
        viewPager2.setAdapter(viewPagerAdapter);

        // 适配器（网上）
//        viewPagerAdapter_1 = new ViewPagerAdapter<>(this,imageUrls,true);

        // 设置 ViewPager2 自动轮播（可选）
        setupAutoSlide();


        // 设置用户头像和用户名
//        username.setText("用户名");  // 这里可以设置动态的用户名，替换为实际的用户名
//        // avatar.setImageResource(R.drawable.avatar);  // 设置用户头像，替换为实际头像资源
//
//        // 设置头像点击事件，跳转到用户信息页面
//        avatar.setOnClickListener(v -> {
//            Intent intent = new Intent(User_Main.this, UserInfoActivity.class);
//            startActivity(intent);
//        });

        // 设置底部导航按钮点击事件
        findViewById(R.id.return_user_main).setOnClickListener(v -> {  // 返回主界面
            Toast.makeText(User_Main.this,"你已经在了哦",Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.nav_short_videos).setOnClickListener(v -> {  // 短视频
            Intent intent = new Intent(User_Main.this, ShortVideosActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_friends).setOnClickListener(v -> { // 好友
            Intent intent = new Intent(User_Main.this, FriendsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_user_info).setOnClickListener(v -> { // 用户信息
            Intent intent = new Intent(User_Main.this, UserInfoActivity.class);
            startActivity(intent);
        });

        // 跳转到树洞倾诉页面
        findViewById(R.id.btn_tree_hole).setOnClickListener(v ->{
            Intent intent = new Intent(User_Main.this, TreeHoleActivity.class);
            startActivity(intent);

        });

        // 跳转到智能对话页面
        findViewById(R.id.btn_intelligent_chat).setOnClickListener(v->{
            Intent intent = new Intent(User_Main.this, SmartChatActivity.class);
            startActivity(intent);
        });

        // 跳转到情感记录页面
        findViewById(R.id.btn_write_feel).setOnClickListener(v->{
            Intent intent = new Intent(User_Main.this, MoodRecordActivity.class);
            startActivity(intent);

        });

        // 跳转到心理资源页面
//        findViewById(R.id.slider_recycler).setOnClickListener(v->{
//            Intent intent = new Intent(User_Main.this, PsychResourcesActivity.class);
//            startActivity(intent);
//
//        });
        viewPager2.setOnClickListener(view -> {
            Intent intent = new Intent(User_Main.this, PsychResourcesActivity.class);
            startActivity(intent);
        });

    }

    // 自动轮播功能
    private void setupAutoSlide() {
        final int[] currentPosition = {0};
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPosition[0] < imageResourceIds.size()) {
                    viewPager2.setCurrentItem(currentPosition[0]);
                    currentPosition[0]++;
                } else {
                    currentPosition[0] = 0;
                    viewPager2.setCurrentItem(currentPosition[0]);
                }
            }
        };

        // 每隔 3 秒自动切换图片
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 3000);
                update.run();
            }
        }, 3000);
    }


}
