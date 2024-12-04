package com.example.pyhy.Login.AIchat;

public class Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";


    String message;
    String setBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSetBy() {
        return setBy;
    }

    public void setSetBy(String setBy) {
        this.setBy = setBy;
    }

    public Message(String message,String setBy) {
        this.message = message;
        this.setBy = setBy;
    }
}



