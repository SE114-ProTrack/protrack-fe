package com.example.protrack.model.response;

import java.util.List;

public class MessagePage {
    private List<MessageResponse> content;
    private int number;
    private int totalPages;

    public List<MessageResponse> getContent() { return content; }
    public int getNumber() { return number; }
    public int getTotalPages() { return totalPages; }
}
