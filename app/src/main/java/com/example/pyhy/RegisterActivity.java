package com.example.pyhy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, ageEditText, emailOrPhoneEditText, passwordEditText, descriptionEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox hobbyReading, hobbySports, hobbyMusic;
    private Button registerButton;

    public RegisterActivity() {
        // 默认构造方法
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 获取控件
        usernameEditText = findViewById(R.id.usernameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        emailOrPhoneEditText = findViewById(R.id.emailOrPhoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        hobbyReading = findViewById(R.id.hobbyReading);
        hobbySports = findViewById(R.id.hobbySports);
        hobbyMusic = findViewById(R.id.hobbyMusic);
        registerButton = findViewById(R.id.registerButton);

        // 注册按钮点击事件
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String age = ageEditText.getText().toString().trim();
            String emailOrPhone = emailOrPhoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            User user = new User(); // 本地临时存储用户信息

            // 获取性别
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedGenderButton = findViewById(selectedGenderId);
            String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "";

            // 获取爱好
            StringBuilder hobbies = new StringBuilder();
            if (hobbyReading.isChecked()) hobbies.append("阅读, ");
            if (hobbySports.isChecked()) hobbies.append("运动, ");
            if (hobbyMusic.isChecked()) hobbies.append("音乐, ");

            // 移除最后一个逗号和空格
            if (hobbies.length() > 0) {
                hobbies.setLength(hobbies.length() - 2);
            }

            // 验证输入字段
            if (username.isEmpty() || age.isEmpty() || emailOrPhone.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "请填写所有必填项", Toast.LENGTH_SHORT).show();
                return;
            }

            // 注册逻辑：保存用户信息
            SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("emailOrPhone", emailOrPhone);
            editor.putString("gender", gender);
            editor.putString("hobbies", hobbies.toString());
            editor.putString("description", description);
            editor.putInt("age", Integer.parseInt(age)); // 假设年龄是整数类型
            editor.apply();

            // 显示注册成功提示
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();

            // 注册成功后跳转到登录页面
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // 关闭当前注册页面
        });
    }
}
