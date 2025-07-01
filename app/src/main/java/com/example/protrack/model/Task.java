package com.example.protrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private String title;
    private String category;
    private String code;
    private LocalDate dueDate;
    public Task(String title, String category, String code, LocalDate dueDate) {
        this.title = title;
        this.category = category;
        this.code = code;
        this.dueDate= dueDate;
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getCode() { return code; }
    public int getDay() { return dueDate.getDayOfMonth(); }
    public int getMonth() { return dueDate.getMonthValue(); }
    public int getYear() { return dueDate.getYear(); }
    // Trả về chuỗi "01" cho ngày
    public String getDayStr() {
        return String.format("%02d", dueDate.getDayOfMonth());
    }

    // Trả về chuỗi "01/07/2025" hoặc "Jul 01 2025"
    public String getFullDateStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dueDate.format(formatter);
    }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate date) { this.dueDate = date; }

    public static class SubTask implements Serializable {
        private String title;
        public SubTask(String title) {
            this.title = title;
        }
        public String getTitle() { return title; }
    }


}

