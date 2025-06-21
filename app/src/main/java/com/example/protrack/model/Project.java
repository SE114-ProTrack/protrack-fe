package com.example.protrack.model;

import java.io.Serializable;

public class Project implements Serializable {
    private Long id;
    private String tenDuAn;
    private String moTa;
    private String thoiGianTao;     //luu tam string
    private Long idNguoiTao;

    public Project(Long id, String title, String dsc, String date, Long userId) {
        this.id = id;
        this.tenDuAn = title;
        this.moTa = dsc;
        this.thoiGianTao = date;
        this.idNguoiTao = userId;
    }

    public Long getId() { return id; }
    public String getTenDuAn() { return tenDuAn; }
    public String getMoTa() { return moTa; }
    public String getThoiGianTao() { return thoiGianTao; }
    public Long getIdNguoiTao() { return idNguoiTao; }
}

