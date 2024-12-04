package com.example.pyhy.Login.PsychRecord;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.R;

public class ResourceDetailActivity extends AppCompatActivity {

    private TextView resourceNameTextView;
    private TextView resourceDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail); // 请确保有对应的布局文件

        // 获取传递过来的资源名称
        String resourceName = getIntent().getStringExtra("resource_name");

        // 初始化界面元素
        resourceNameTextView = findViewById(R.id.resourceNameTextView);
        resourceDetailTextView = findViewById(R.id.resourceDetailTextView);

        // 设置资源名称
        resourceNameTextView.setText(resourceName);

        // 根据资源名称显示不同的资源详细信息
        String resourceDetails = getResourceDetails(resourceName);
        resourceDetailTextView.setText(resourceDetails);
    }

    // 根据资源名称返回对应的详细信息
    private String getResourceDetails(String resourceName) {
        switch (resourceName) {
            case "心理健康指南":
                return "心理健康指南内容......";
            case "压力管理技巧":
                return "压力管理技巧内容......";
            case "焦虑症自我帮助":
                return "焦虑症自我帮助内容......";
            case "情绪调节方法":
                return "情绪调节方法内容......";
            default:
                return "没有找到详细信息";
        }
    }
}
