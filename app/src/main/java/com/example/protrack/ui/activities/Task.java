package com.example.protrack.ui.activities;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String category;
    private String code;

    public Task(String title, String category, String code) {
        this.title = title;
        this.category = category;
        this.code = code;
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getCode() { return code; }

    public static class SubTask implements Serializable {
        private String title;

        public SubTask(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }


}

