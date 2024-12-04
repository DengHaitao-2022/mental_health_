package com.example.pyhy.Login.DiaryWrite;
import java.io.Serializable;

public class Diary implements Serializable {
    private int id;
    private String text;
    private String imagePath;
    private String timestamp;

    // 构造函数
    public Diary(int id, String text, String imagePath, String timestamp) {
        this.id = id;
        this.text = text;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
    }

    // Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
