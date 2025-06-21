package com.example.protrack.model;

public class Member {
    private String name;
    private String role;
    private String statusText; // e.g. "Offline 26m ago"
    private int avatarUrl;   // private String avatarUrl nếu dùng ảnh từ internet

    private boolean isSelected;

    public Member(String name, String role, String statusText, int avatarUrl, boolean isSelected) {
        this.name = name;
        this.role = role;
        this.statusText = statusText;
        this.avatarUrl = avatarUrl;
        this.isSelected = isSelected;
    }

    // Getters và Setters

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getStatusText() {
        return statusText;
    }

    public int getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
