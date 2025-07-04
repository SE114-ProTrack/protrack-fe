package com.example.protrack.model.response;

import java.util.List;

public class TaskPageResponse {
    private List<TaskResponse> content;
    private int totalPages;
    private int number; // current page
    private int size;   // page size

    public List<TaskResponse> getContent() { return content; }
    public void setContent(List<TaskResponse> content) { this.content = content; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}

