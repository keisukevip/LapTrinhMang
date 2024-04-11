package com.acer.model;

import java.sql.*;

public class TaiKhoan {

    private String tenDangNhap;
    private String matKhau;

    // Constructor
    public TaiKhoan(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }
}
