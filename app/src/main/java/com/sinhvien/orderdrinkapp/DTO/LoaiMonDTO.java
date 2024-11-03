package com.sinhvien.orderdrinkapp.DTO;

public class LoaiMonDTO {

    int MaLoai;
    String TenLoai;
    String HinhAnh; // Đổi từ byte[] sang String


    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return HinhAnh; // Sửa getter thành String
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh; // Sửa setter thành String
    }
}
