package com.example.protrack.model.response;

import java.util.UUID;

public class LabelResponse {
    private String labelId;
    private String labelName;
    private String description;
    private String color;
    private String projectId;
    private String projectName;

    public LabelResponse(String labelId, String labelName, String description, String color, String projectId, String projectName) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.description = description;
        this.color = color;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
