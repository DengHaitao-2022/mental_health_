package com.example.pyhy.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.MainActivity;
import com.example.pyhy.R;

public class UserInfoActivity extends AppCompatActivity {

    // 声明控件
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView accountTextView;
    private EditText bioEditText;
    private EditText interestsEditText;
    private TextView viewDohHoleLabel;
    private LinearLayout dohHoleContainer;
    private Button logoutbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局
        setContentView(R.layout.user_profile);

        // 初始化控件
        avatarImageView = findViewById(R.id.avatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        accountTextView = findViewById(R.id.accountTextView);
        bioEditText = findViewById(R.id.bioEditText);
        interestsEditText = findViewById(R.id.interestsEditText);
        viewDohHoleLabel = findViewById(R.id.viewDohHoleLabel);
        dohHoleContainer = findViewById(R.id.dohHoleContainer);
        logoutbutton = findViewById(R.id.logoutButton);

        // 设置用户信息（可以从 Intent 或数据库加载）
        // 这里我们假设设置了一个示例用户信息


        // 设置头像
        avatarImageView.setImageResource(R.drawable.user_head_png);  // 替换成实际头像图片

        // 设置用户名和账号
        usernameTextView.setText("用户名: JohnDoe");
        accountTextView.setText("账号: johndoe@gmail.com");

        // 设置个人简介和兴趣爱好（假设从数据库获取）
        Intent intent_des = getIntent();
        String des_ = intent_des.getStringExtra("descript");

        // 空值检查，避免空指针异常
        if (des_ == null || des_.equals("")) {
            bioEditText.setText("可以添加个人简介");
        } else {
            bioEditText.setText(des_);
        }

        // 假设兴趣爱好也是从数据库或其他地方获取
        String interests = "旅游、编程、摄影";
        interestsEditText.setText("兴趣: " + interests);


        // 设置树洞信息（假设动态加载数据）
        // 示例树洞内容
        setDohHoleContent();
        
        findViewById(R.id.return_user_main).setOnClickListener(v -> {  //  返回主页
            Intent intent = new Intent(UserInfoActivity.this, TreeHoleActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_short_videos).setOnClickListener(v -> {  // 短视频
            Intent intent = new Intent(UserInfoActivity.this, ShortVideosActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_friends).setOnClickListener(v -> { // 好友
            Intent intent = new Intent(UserInfoActivity.this, FriendsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_user_info).setOnClickListener(v -> { // 用户信息
            Toast.makeText(UserInfoActivity.this,"已经在了哦",Toast.LENGTH_SHORT).show();
        });

        logoutbutton.setOnClickListener(view -> {
            // 创建一个确认退出登录的对话框
            new AlertDialog.Builder(view.getContext())
                    .setTitle("确认退出登录")
                    .setMessage("您确定要退出登录吗？")
                    .setPositiveButton("是", (dialog, which) -> {
                        // 清除登录状态
                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("is_logged_in");  // 删除登录状态
                        editor.remove("user_token");  // 删除保存的用户信息（如果有）
                        editor.apply();  // 保存更改

                        // 跳转到登录页面（MainActivity）
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                        finish();  // 退出当前页面
                    })
                    .setNegativeButton("否", null)  // 用户点击"否"时什么都不做
                    .show();
        });


    }

    // 设置树洞内容的示例方法
    private void setDohHoleContent() {
        // 动态添加树洞内容（示例）
        for (int i = 0; i < 2; i++) {
            LinearLayout dohHoleLayout = new LinearLayout(this);
            dohHoleLayout.setOrientation(LinearLayout.HORIZONTAL);
            dohHoleLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            dohHoleLayout.setPadding(0, 0, 0, 12);

            // 树洞图片
            ImageView dohImageView = new ImageView(this);
            dohImageView.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
//            dohImageView.setImageResource(R.drawable.doh_hole_image);  // 设置树洞图片

            // 树洞文本
            TextView dohTextView = new TextView(this);
            dohTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            dohTextView.setText("这是树洞内容的描述...");
            dohTextView.setTextSize(14);
//            dohTextView.setTextColor(getResources().getColor(R.color.textPrimary));  // 设置字体颜色

            // 添加子控件到 LinearLayout
            dohHoleLayout.addView(dohImageView);
            dohHoleLayout.addView(dohTextView);

            // 将树洞内容添加到容器中
            dohHoleContainer.addView(dohHoleLayout);
        }
    }
}
