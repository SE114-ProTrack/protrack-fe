package com.example.protrack.model.response;

import java.util.List;

public class PageProjectResponse {

    private List<ProjectResponse> content;
    private int totalElements;
    private int totalPages;
    private int size;
    private int number;
    private boolean first;
    private boolean last;
    private int numberOfElements;
    private boolean empty;

    // Bạn có thể thêm nếu cần: "sort" và "pageable"

    // Getters
    public List<ProjectResponse> getContent() { return content; }

    public int getTotalElements() { return totalElements; }

    public int getTotalPages() { return totalPages; }

    public int getSize() { return size; }

    public int getNumber() { return number; }

    public boolean isFirst() { return first; }

    public boolean isLast() { return last; }

    public int getNumberOfElements() { return numberOfElements; }

    public boolean isEmpty() { return empty; }
}

