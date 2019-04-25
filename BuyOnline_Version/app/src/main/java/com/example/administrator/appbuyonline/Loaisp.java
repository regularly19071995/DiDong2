package com.example.administrator.appbuyonline;

public class Loaisp {
    public int id;
    public String tenLoaisp;
    public String hinhanhLoaisp;

    public Loaisp(int id, String tenLoaisp, String hinhanhLoaisp) {
        this.id = id;
        this.tenLoaisp = tenLoaisp;
        this.hinhanhLoaisp = hinhanhLoaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaisp() {
        return tenLoaisp;
    }

    public void setTenLoaisp(String tenLoaisp) {
        this.tenLoaisp = tenLoaisp;
    }

    public String getHinhanhLoaisp() {
        return hinhanhLoaisp;
    }

    public void setHinhanhLoaisp(String hinhanhLoaisp) {
        this.hinhanhLoaisp = hinhanhLoaisp;
    }
}
