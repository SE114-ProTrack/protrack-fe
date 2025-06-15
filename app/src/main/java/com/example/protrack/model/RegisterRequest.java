package com.example.protrack.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RegisterRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("matKhau")
    private String matKhau;
    @SerializedName("hoTen")
    private String hoTen;
    @SerializedName("ngaySinh")
    private String ngaySinh;    // luu tam string
    @SerializedName("gioiTinh")
    private String gioiTinh;
    @SerializedName("dienThoai")
    private String dienThoai;
    @SerializedName("diaChi")
    private String diaChi;

    // Constructor
    public RegisterRequest(String email, String matKhau, String hoTen, String ngaySinh, String gioiTinh, String dienThoai, String diaChi) {
        this.email = email;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    // Getters
    public String getEmail() { return email; }
    public String getMatKhau() { return matKhau; }
    public String getHoTen() { return hoTen; }
    public String getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDienThoai() { return dienThoai; }
    public String getDiaChi() { return diaChi; }

}
