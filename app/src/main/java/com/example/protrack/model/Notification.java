package com.example.protrack.model;

public class Notification {
    private int userAvatarResId; // Resource ID cho avatar (hoặc có thể là URL nếu bạn load từ mạng)
    private String userName;
    private String statusMessage;

    public Notification(int userAvatarResId, String userName, String statusMessage) {
        this.userAvatarResId = userAvatarResId;
        this.userName = userName;
        this.statusMessage = statusMessage;
    }

    public int getUserAvatarResId() {
        return userAvatarResId;
    }

    public void setUserAvatarResId(int userAvatarResId) {
        this.userAvatarResId = userAvatarResId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
