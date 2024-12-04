package com.example.pyhy.Login.DiaryWrite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.net.Uri;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;

    // 数据库表和列名
    public static final String TABLE_DIARY = "diary";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // 构造函数
    public DiaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 创建数据库表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DIARY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TEXT + " TEXT,"
                + COLUMN_IMAGE_PATH + " TEXT,"
                + COLUMN_TIMESTAMP + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    // 数据库升级时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        onCreate(db);
    }

    // 添加日记
    public void addDiary(Diary diary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, diary.getText());
        values.put(COLUMN_IMAGE_PATH, diary.getImagePath().toString());  // 确保转化为字符串
        values.put(COLUMN_TIMESTAMP, getCurrentTimestamp());

        db.insert(TABLE_DIARY, null, values);
        db.close();
    }

    // 获取所有日记
    public ArrayList<Diary> getAllDiaries() {
        ArrayList<Diary> diaries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_DIARY + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                    @SuppressLint("Range") Uri imagePath = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH)));  // 转换为 Uri
                    @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                    diaries.add(new Diary(id, text, imagePath, timestamp));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return diaries;
    }

    // 根据ID获取单个日记
    public Diary getDiaryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_DIARY,
                    new String[]{COLUMN_ID, COLUMN_TEXT, COLUMN_IMAGE_PATH, COLUMN_TIMESTAMP},
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") int diaryId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
                @SuppressLint("Range") Uri imagePath = Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH)));  // 转换为 Uri
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                return new Diary(diaryId, text, imagePath, timestamp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return null;  // 如果没有找到该日记，返回 null
    }

    // 获取当前时间戳
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
