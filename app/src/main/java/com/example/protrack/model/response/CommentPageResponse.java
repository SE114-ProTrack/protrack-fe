package com.example.protrack.model.response;

import java.util.List;

public class CommentPageResponse {
    private List<CommentResponse> content;
    private int totalPages;
    private int number; // current page
    private int size;

    public List<CommentResponse> getContent() { return content; }
    public void setContent(List<CommentResponse> content) { this.content = content; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}

