package com.example.pyhy.Login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.Login.DiaryWrite.Diary;
import com.example.pyhy.Login.DiaryWrite.DiaryDatabaseHelper;
import com.example.pyhy.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.example.pyhy.Login.DiaryWrite.*;

public class MoodRecordActivity extends AppCompatActivity {

    private Button buttonCreateDiary;
    private RecyclerView recyclerView;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> diaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_record);  // 设置布局文件

        buttonCreateDiary = findViewById(R.id.buttonCreateDiary);  // 获取创建日记按钮
        recyclerView = findViewById(R.id.recyclerView);  // 获取RecyclerView

        // 设置RecyclerView的LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // 使用LinearLayoutManager进行垂直排列

        // 获取数据库中的所有日记
        DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(this);
        diaryList = dbHelper.getAllDiaries();  // 假设getAllDiaries()方法返回所有日记记录

        // 创建Adapter，并绑定到RecyclerView
        diaryAdapter = new DiaryAdapter(diaryList);
        recyclerView.setAdapter(diaryAdapter);

        // 设置创建日记按钮的点击事件
        buttonCreateDiary.setOnClickListener(v -> {
            Intent intent = new Intent(MoodRecordActivity.this, CreateDiaryActivity.class);  // 跳转到创建日记界面
            startActivity(intent);
        });
    }

    // 在此处可能需要刷新RecyclerView数据的逻辑
    @Override
    protected void onResume() {
        super.onResume();
        DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(this);
        diaryList.clear();
        diaryList.addAll(dbHelper.getAllDiaries());
        diaryAdapter.notifyDataSetChanged();  // 通知Adapter刷新数据
    }
}
