package com.example.protrack.model;

public class Chat implements ChatItem {
    private String content;
    private boolean isSent; // true: sent message, false: received

    @Override
    public int getType() { return TYPE_MESSAGE; }

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
