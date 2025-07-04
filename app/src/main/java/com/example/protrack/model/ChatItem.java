package com.example.protrack.model;

public interface ChatItem {
    int TYPE_MESSAGE = 0;
    int TYPE_HEADER = 1;

    int getType();
}