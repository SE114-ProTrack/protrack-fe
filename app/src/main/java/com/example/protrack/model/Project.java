package com.example.protrack.model;

import java.io.Serializable;

public class Project implements Serializable {
    private String id;
    private String tenDuAn;
    private String moTa;
    private String anhBiaDuAn;
    private String thoiGianTao;     //luu tam string
    private String tenNguoiTao;
    private int tongTask;
    private int taskHoanThanh;

    public Project(String id, String title, String dsc, String imgUrl, String date,
                   String creatorName, int totalTask, int completedTask) {
        this.id = id;
        this.tenDuAn = title;
        this.moTa = dsc;
        this.anhBiaDuAn = imgUrl;
        this.thoiGianTao = date;
        this.tenNguoiTao = creatorName;
        this.tongTask = totalTask;
        this.taskHoanThanh = completedTask;
    }

    public String getId() {
        return id;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getAnhBiaDuAn() {
        return anhBiaDuAn;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public String getTenNguoiTao() {
        return tenNguoiTao;
    }

    public int getTaskHoanThanh() {
        return taskHoanThanh;
    }

    public int getTongTask() {
        return tongTask;
    }

}

