package com.example.protrack.model;

public class ChatHeader implements ChatItem {
    private final String label;
    public ChatHeader(String label) { this.label = label; }
    public String getLabel() { return label; }
    @Override
    public int getType() { return TYPE_HEADER; }
}