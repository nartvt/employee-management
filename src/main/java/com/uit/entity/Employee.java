package com.uit.entity;

import java.time.LocalDate;

public class Employee {
    private String maNhanvien;
    private String hoNhanVien;
    private String tenNhanVien;

    private String fullName;

    private String identifier;
    private LocalDate ngaySinh;
    private String quocTich;

    private LocalDate ngayVaoLam;
    private String diaChi;
    private String soDT;
    private String phongBan;
    private String vitri;

    public String getMaNhanvien() {
        return maNhanvien;
    }

    public void setMaNhanvien(String maNhanvien) {
        this.maNhanvien = maNhanvien;
    }

    public String getHoNhanVien() {
        return hoNhanVien;
    }

    public void setHoNhanVien(String hoNhanVien) {
        this.hoNhanVien = hoNhanVien;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        this.fullName =  hoNhanVien.concat(" ").concat(tenNhanVien);
        return fullName;
    }
}
