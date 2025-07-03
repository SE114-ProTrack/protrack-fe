package com.example.protrack.model.response;

public class NotificationResponse {

    private String notificationId;
    private String senderAvt;
    private String senderName;
    private String receiverId;
    private String receiverFullName;
    private String type;
    private String content;
    private boolean isRead;
    private String timestamp;
    private String actionUrl;

    public NotificationResponse(String notificationId, String senderAvt, String senderName,
                                String receiverId, String receiverFullName, String type,
                                String content, boolean isRead, String timestamp, String actionUrl) {
        this.notificationId = notificationId;
        this.senderAvt = senderAvt;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverFullName = receiverFullName;
        this.type = type;
        this.content = content;
        this.isRead = isRead;
        this.timestamp = timestamp;
        this.actionUrl = actionUrl;
    }

    // Getters
    public String getNotificationId() {
        return notificationId;
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

    public String getContent() {
        return content;
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
