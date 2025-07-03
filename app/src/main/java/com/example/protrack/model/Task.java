package com.example.protrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String labelId;
    private Attachment attachment;
    private List<String> assigneeIds;
    private String icon;
    private String color;
    private String projectName;


    public Task(String id, String title, String description, LocalDate dueDate,
                String status, String labelId, Attachment attachment,
                List<String> assigneeIds, String icon, String color, String projectName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.labelId = labelId;
        this.attachment = attachment;
        this.assigneeIds = assigneeIds;
        this.icon = icon;
        this.color = color;
        this.projectName = projectName;
    }
    public String getProjectName() {
        return projectName;
    }


    // Getter
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public String getLabelId() { return labelId; }
    public Attachment getAttachment() { return attachment; }
    public List<String> getAssigneeIds() { return assigneeIds; }
    public String getIcon() { return icon; }
    public String getColor() { return color; }


    // Nested Attachment class
    public static class Attachment implements Serializable {
        private String id;
        private String name;
        private String filePath;
        private String fileUrl;
        private String fileType;

        public Attachment(String id, String name, String filePath, String fileUrl, String fileType) {
            this.id = id;
            this.name = name;
            this.filePath = filePath;
            this.fileUrl = fileUrl;
            this.fileType = fileType;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getFilePath() { return filePath; }
        public String getFileUrl() { return fileUrl; }
        public String getFileType() { return fileType; }
    }
}
