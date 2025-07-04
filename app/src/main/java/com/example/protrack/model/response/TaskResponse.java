package com.example.protrack.model.response;

import java.util.List;

public class TaskResponse {
    private String taskId;
    private String taskName;
    private String description;
    private String status;
    private String deadline;
    private String icon;
    private String color;
    private String projectName;
    private List<LabelResponse> labels;
    private List<TaskAttachmentResponse> attachments;

    // Getter & Setter
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public List<LabelResponse> getLabels() { return labels; }
    public List<TaskAttachmentResponse> getAttachments() { return attachments; }
}

