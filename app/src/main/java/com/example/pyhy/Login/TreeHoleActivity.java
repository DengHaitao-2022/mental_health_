package com.example.pyhy.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pyhy.Login.TreeHole.PostTreeHoleActivity;
import com.example.pyhy.Login.TreeHole.TreeHole;
import com.example.pyhy.Login.TreeHole.TreeHoleAdapter;
import com.example.pyhy.R;

import java.util.ArrayList;
import java.util.List;

public class TreeHoleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TreeHoleAdapter adapter;
    private List<TreeHole> treeHoleList = new ArrayList<>();
    private ImageButton postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_hole);

        recyclerView = findViewById(R.id.recyclerView);
        postButton = findViewById(R.id.addPostImageView);

        // 设置 RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreeHoleAdapter(this, treeHoleList);
        recyclerView.setAdapter(adapter);

        // 添加示例数据
        treeHoleList.add(new TreeHole("用户1", "这是第一条树洞内容", "2024年12月3日 10:00"));
        treeHoleList.add(new TreeHole("用户2", "这是第二条树洞内容", "2024年12月3日 11:00"));
        adapter.notifyDataSetChanged();

        // 发布树洞按钮
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动发布树洞活动
                Intent intent = new Intent(TreeHoleActivity.this, PostTreeHoleActivity.class);
                startActivityForResult(intent, 1);  // 请求码为 1
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String newContent = data.getStringExtra("newContent");
            String postTime = data.getStringExtra("postTime");

            // 更新树洞列表
            treeHoleList.add(new TreeHole("新用户", newContent, postTime));
            adapter.notifyDataSetChanged();  // 刷新 RecyclerView
        }
    }
}
