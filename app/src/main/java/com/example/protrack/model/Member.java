package com.example.protrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String name;
    private String role;
    private String statusText;
    private int avatarUrl;
    private boolean isSelected;

    public Member(String name, String role, String statusText, int avatarUrl, boolean isSelected) {
        this.name = name;
        this.role = role;
        this.statusText = statusText;
        this.avatarUrl = avatarUrl;
        this.isSelected = isSelected;
    }

    // Constructor dùng cho Parcelable
    protected Member(Parcel in) {
        name = in.readString();
        role = in.readString();
        statusText = in.readString();
        avatarUrl = in.readInt();
        isSelected = in.readByte() != 0; // true nếu byte != 0
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(role);
        dest.writeString(statusText);
        dest.writeInt(avatarUrl);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getAvatarResId() {
        return avatarUrl;
    }
}
