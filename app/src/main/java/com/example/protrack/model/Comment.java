package com.example.protrack.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private String name;
//    private String user;

    private String time;
    private String content;
    private int avatarResId;


    public Comment(String name, String time, String content, int avatarResId) {
        this.name = name;
        this.time = time;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
    public int getAvatarResId() {
        return avatarResId;
    }
//    public String getUser() {
//        return user;
//    }

}
