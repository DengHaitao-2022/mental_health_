package com.example.pyhy.Login.DiaryWrite;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pyhy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateDiaryActivity extends AppCompatActivity {

    private EditText editTextDiary;
    private Button buttonSaveDiary, buttonSelectImage;
    private ImageView imageView;
    private String imagePath = "res/drawable/p_1.jpg";  // 用于存储图片的本地路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_diary);

        editTextDiary = findViewById(R.id.editTextDiary);
        buttonSaveDiary = findViewById(R.id.buttonSaveDiary);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imageView = findViewById(R.id.imageView);

        // 图片选择按钮点击事件
        buttonSelectImage.setOnClickListener(v -> {
            // 使用Intent选择图片
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        // 保存日记按钮点击事件
        buttonSaveDiary.setOnClickListener(v -> {
            String diaryText = editTextDiary.getText().toString();
            if (diaryText.isEmpty()) {
                Toast.makeText(CreateDiaryActivity.this, "日记内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Diary newDiary = new Diary(0, diaryText, imagePath, timestamp);

                // 将日记保存到数据库
                DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(CreateDiaryActivity.this);
                dbHelper.addDiary(newDiary);

                Toast.makeText(CreateDiaryActivity.this, "日记保存成功", Toast.LENGTH_SHORT).show();
                finish();  // 保存完后关闭当前活动，返回上一页
            }
        });
    }

    // 处理图片选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            Uri imageUri = data.getData();
            imagePath = getRealPathFromURI(imageUri);  // 获取图片的本地路径
            imageView.setImageURI(imageUri);  // 显示图片
        }
    }

    // 从URI获取本地文件路径
    private String getRealPathFromURI(Uri uri) {
        String filePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }
}
