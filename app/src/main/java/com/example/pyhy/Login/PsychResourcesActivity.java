package com.example.pyhy.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.Login.PsychRecord.ResourceDetailActivity;
import com.example.pyhy.R;

import java.util.ArrayList;

public class PsychResourcesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> resourcesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych_resources); // 请确保有对应的布局文件

        listView = findViewById(R.id.resourcesListView);

        // 创建一些示例资源
        resourcesList = new ArrayList<>();
        resourcesList.add("心理健康指南");
        resourcesList.add("压力管理技巧");
        resourcesList.add("焦虑症自我帮助");
        resourcesList.add("情绪调节方法");

        // 创建适配器并设置给 ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resourcesList);
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取点击的资源项
                String selectedResource = resourcesList.get(position);

                // 启动查看资源详情页面
                Intent intent = new Intent(PsychResourcesActivity.this, ResourceDetailActivity.class);
                intent.putExtra("resource_name", selectedResource); // 将资源名称传递给详情页面
                startActivity(intent);
            }
        });
    }
}
