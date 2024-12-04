package com.example.pyhy;

import com.example.pyhy.Login.User_Main;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private TextView registerTextView;
    User user = new User(); // 本地临时保存注册信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 在 onCreate 中初始化 RelativeLayout
        relativeLayout = findViewById(R.id.loginRelative);

        // 设置窗口Insets监听器
        ViewCompat.setOnApplyWindowInsetsListener(relativeLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取界面上的控件
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        registerTextView = findViewById(R.id.registerTextView);

        // 检查 SharedPreferences 中是否已登录
        checkLoginStatus();

        // 设置登录按钮点击事件
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                // 进行简单的验证
                if (username.isEmpty()) {
                    usernameEditText.setError("用户名不能为空");
                }
                if (password.isEmpty()) {
                    passwordEditText.setError("密码不能为空");
                }

            } else {
                // 登录逻辑
                SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("is_logged_in", true);
                editor.apply();
//                 跳转到主界面
                    try {
                        Intent intent = new Intent(MainActivity.this, User_Main.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.d("denglu", "Error launching User_Main activity: " + e.getMessage());
                    } finally {
                        finish(); // 关闭当前登录界面
                    }
//                if (username.equals(user.getPhone().toString()) && password.equals(user.getPasswd())) {
//                    // 登录成功，保存登录状态
//                    SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putBoolean("is_logged_in", true);
//                    editor.apply();
//
//                    // 跳转到主界面
//                    try {
//                        Intent intent = new Intent(MainActivity.this, User_Main.class);
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        Log.d("denglu", "Error launching User_Main activity: " + e.getMessage());
//                    } finally {
//                        finish(); // 关闭当前登录界面
//                    }
//                } else {
//                    Toast.makeText(this, "用户名或者密码错误,请重新输入", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        // 设置注册链接点击事件
        registerTextView.setOnClickListener(v -> {
            // 跳转到注册页面
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 立即注册按钮
        registerButton.setOnClickListener(v -> {
            // 跳转到注册界面
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, User_Main.class);
            startActivity(intent);
            finish();
        }
    }
}
