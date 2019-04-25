package com.example.administrator.appbuyonline;

import java.io.Serializable;

public class SanPham implements Serializable{
    public int id;
    public String tensp;
    public Integer gia;
    public String hinhanhsp;
    public String motasp;
    public int idsp;

    public SanPham(int id, String tensp, Integer gia, String hinhanhsp, String motasp, int idsp) {
        this.id = id;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.idsp = idsp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }
}
