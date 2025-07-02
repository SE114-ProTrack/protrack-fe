package com.example.protrack.model;

import java.util.UUID;

public class Notification {
    private String id;
    private String userAvatarUrl;     // Đường dẫn URL ảnh avatar
    private String userName;
    private String message;

    public Notification(String id, String userAvatarUrl, String userName, String message) {
        this.id = id;
        this.userAvatarUrl = userAvatarUrl;
        this.userName = userName;
        this.message = message;
    }

    // Getter và Setter

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getUserAvatarUrl() { return userAvatarUrl; }

    public void setUserAvatarUrl(String userAvatarUrl) { this.userAvatarUrl = userAvatarUrl; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
