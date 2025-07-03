package com.example.protrack.model.response;

public class ProjectResponse {
    private String projectId;
    private String projectName;
    private String description;
    private String bannerUrl;
    private String createTime;
    private String creatorFullName;
    private int allTasks;
    private int completedTasks;

    public ProjectResponse(String projectId, String projectName, String description,
                           String bannerUrl, String createTime, String creatorFullName,
                           int allTasks, int completedTasks) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.createTime = createTime;
        this.creatorFullName = creatorFullName;
        this.allTasks =allTasks;
        this.completedTasks=completedTasks;
    }

    // Getters
    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public int getAllTasks() {
        return allTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }
}
