package com.example.pyhy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.Login.UserInfoActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpExample";
    private static final String API_URL = "https://111.229.103.219:8080/register";  // API URL
    private static final int PICK_IMAGE_REQUEST = 1;  // 请求码，用于标识图片选择器返回的结果

    private EditText usernameEditText, ageEditText, emailOrPhoneEditText, passwordEditText, descriptionEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox hobbyReading, hobbySports, hobbyMusic;
    private Button registerButton;
    private ImageView profileImageView;  // 用户头像

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
        profileImageView = findViewById(R.id.profileImageView);  // 头像控件

        // 用户头像点击事件，打开图片选择器
        profileImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");  // 只选择图片
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // 注册按钮点击事件
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String age = ageEditText.getText().toString().trim();
            String emailOrPhone = emailOrPhoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

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

            // 构造 JSON 数据
            String json = String.format("{\n" +
                    "  \"username\": \"%s\",\n" +
                    "  \"phone\": \"%s\",\n" +
                    "  \"password\": \"%s\",\n" +
                    "  \"role\": 0,\n" +
                    "  \"headerLink\": \"string\"\n" +
                    "}", username, emailOrPhone, password);

            // 发送请求
            new Thread(() -> sendPostRequest(json)).start();

            // 显示注册成功提示
            Toast.makeText(this, "注册请求已发送！", Toast.LENGTH_SHORT).show();
        });
    }

    // 处理图片选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // 获取选中的图片 URI
            Uri imageUri = data.getData();
            try {
                // 将选中的图片显示到 ImageView 中
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "加载图片失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 发送 POST 请求
    private void sendPostRequest(String json) {
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 设置请求体格式为 JSON
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, mediaType);

        // 创建请求
        Request request = new Request.Builder()
                .url(API_URL)  // 替换为你实际的 API 地址
                .post(body)
                .build();

        try {
            // 执行请求并获取响应
            Response response = client.newCall(request).execute();

            // 如果响应成功，打印响应内容
            if (response.isSuccessful()) {
                Log.d(TAG, "Response: " + response.body().string());
            } else {
                Log.e(TAG, "Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Request failed", e);
        }
    }
}
