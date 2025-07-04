package com.example.protrack.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentResponse {
    private String commentId;
    private String taskId;
    private String userId;
    private String userName;
    private String content;
    private LocalDateTime timestamp;

    public CommentResponse(String commentId, String taskId, String userId, String userName, String content, LocalDateTime timestamp) {
        this.commentId = commentId;
        this.taskId = taskId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
