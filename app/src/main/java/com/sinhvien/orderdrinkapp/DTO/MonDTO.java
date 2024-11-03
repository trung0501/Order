package com.sinhvien.orderdrinkapp.DTO;

public class MonDTO {

    private int MaMon, MaLoai;
    private String TenMon, GiaTien, TinhTrang;
    private String HinhAnh; // Đổi từ byte[] sang String

    public int getMaMon() {
        return MaMon;
    }

    public void setMaMon(int maMon) {
        MaMon = maMon;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public String getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(String giaTien) {
        GiaTien = giaTien;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getHinhAnh() {
        return HinhAnh; // Sửa getter thành String
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh; // Sửa setter thành String
    }
}
