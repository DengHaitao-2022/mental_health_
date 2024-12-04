package com.example.pyhy.Login.TreeHole;

public class TreeHole {
    private String userName;
    private String postContent;
    private String postTime;

    public TreeHole(String userName, String postContent, String postTime) {
        this.userName = userName;
        this.postContent = postContent;
        this.postTime = postTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
