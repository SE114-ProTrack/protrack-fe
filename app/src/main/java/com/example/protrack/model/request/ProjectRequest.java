package com.example.protrack.model.request;

public class ProjectRequest {
    private String projectName;
    private String description;
    private String bannerUrl; // optional

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public ProjectRequest(String projectName, String description, String bannerUrl) {
        this.projectName = projectName;
        this.description = description;
        this.bannerUrl = bannerUrl;
    }
}
