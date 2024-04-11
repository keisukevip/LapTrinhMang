package com.acer.model;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class CongViec {

    private int id;
    private String tenCongViec;
    private String nguoiThucHien;
    private String trangThai;

    // Constructor
    public CongViec(int id, String tenCongViec, String nguoiThucHien, String trangThai) {
        this.id = id;
        this.tenCongViec = tenCongViec;
        this.nguoiThucHien = nguoiThucHien;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public String getNguoiThucHien() {
        return nguoiThucHien;
    }

    public String getTrangThai() {
        return trangThai;
    }
}
