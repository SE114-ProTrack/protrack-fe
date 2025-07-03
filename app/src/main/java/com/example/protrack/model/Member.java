package com.example.protrack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String id;
    private String name;
    private String role;
    private String status;
    private int avatarResId; // Dùng int để lưu ảnh từ resource
    private boolean selected;

    public Member(String id, String name, String role, String status, int avatarResId, boolean selected) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = status;
        this.avatarResId = avatarResId;
        this.selected = selected;
    }

    protected Member(Parcel in) {
        id = in.readString();
        name = in.readString();
        role = in.readString();
        status = in.readString();
        avatarResId = in.readInt();
        selected = in.readByte() != 0;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(role);
        parcel.writeString(status);
        parcel.writeInt(avatarResId);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
