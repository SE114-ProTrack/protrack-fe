package com.example.protrack.model;

public class Chat {
    private String content;
    private boolean isSent; // true: sent message, false: received

    public Chat(String content, boolean isSent) {
        this.content = content;
        this.isSent = isSent;
    }

    public String getContent() {
        return content;
    }

    public boolean isSent() {
        return isSent;
    }
}
