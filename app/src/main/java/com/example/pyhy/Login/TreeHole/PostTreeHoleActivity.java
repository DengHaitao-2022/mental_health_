package com.example.pyhy.Login.TreeHole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.R;

public class PostTreeHoleActivity extends AppCompatActivity {
    private EditText postContentEditText;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tree_hole);

        postContentEditText = findViewById(R.id.postContentEditText);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = postContentEditText.getText().toString().trim();
                if (content.isEmpty()) {
                    Toast.makeText(PostTreeHoleActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    // 在这里可以执行发布逻辑，例如将内容发送到服务器

                    // 发布成功，返回结果
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newContent", content);
                    resultIntent.putExtra("postTime", "2024年12月3日 12:00");  // 示例时间
                    setResult(RESULT_OK, resultIntent);
                    finish();  // 发布成功后关闭当前页面
                }
            }
        });
    }
}
