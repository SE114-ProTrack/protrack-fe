package com.example.protrack.model;

public class ChatSummary {
    private int avatarResId;       // Nếu lấy ảnh từ URL (hoặc Uri), dùng String hoặc Uri thay vì int
    private String userName;       // Tên người dùng (VD: Tien Tom)
    private String lastMessage;    // Tin nhắn cuối cùng
    private String timeAgo;        // Thời gian nhắn (VD: 10 mins ago)
    private int unreadCount;       // Số lượng tin chưa đọc

    private String avatarUrl;
    private String userId;


    public ChatSummary(int avatarResId, String userName, String lastMessage, String timeAgo, int unreadCount, String avatarUrl, String userId) {
        this.avatarResId = avatarResId;
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.timeAgo = timeAgo;
        this.unreadCount = unreadCount;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // Getters
    public int getAvatarResId() {
        return avatarResId;
    }

    public String getSenderName() {
        return userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    // Setters
    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
