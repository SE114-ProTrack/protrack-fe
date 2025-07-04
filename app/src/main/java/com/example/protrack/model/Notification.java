package com.example.protrack.model;

public class Notification {
    private String id;
    private String senderAvt;     // Đường dẫn URL ảnh avatar
    private String senderName;
    private String receiverId;
    private String receiverFullName;
    private String type;
    private String message;
    private boolean isRead;
    private String timestamp;
    private String actionUrl;

    public Notification(String id, String senderAvt, String senderName,
                        String receiverId, String receiverFullName, String type,
                        String message, boolean isRead, String timestamp, String actionUrl) {
        this.id = id;
        this.senderAvt = senderAvt;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverFullName = receiverFullName;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.timestamp = timestamp;
        this.actionUrl = actionUrl;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getSenderAvt() {
        return senderAvt;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getActionUrl() {
        return actionUrl;
    }
}
