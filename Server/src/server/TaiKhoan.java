package server;

import java.sql.*;

public class TaiKhoan {

    private String tenDangNhap;
    private String matKhau;

    // Constructor
    public TaiKhoan(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    // Phương thức để so sánh thông tin đăng nhập với dữ liệu trong CSDL
    public boolean kiemTraDangNhap() throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        boolean loginSuccess = false;
        try {
            databaseHelper.connect();
            String query = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";
            PreparedStatement preparedStatement = databaseHelper.getConnection().prepareStatement(query);
            preparedStatement.setString(1, tenDangNhap);
            preparedStatement.setString(2, matKhau);
            ResultSet resultSet = preparedStatement.executeQuery();
            loginSuccess = resultSet.next(); // Kiểm tra có kết quả từ CSDL không
        } finally {
            if (databaseHelper.getConnection() != null) { // Kiểm tra xem kết nối có tồn tại không trước khi đóng
                databaseHelper.disconnect(); // Đóng kết nối nếu tồn tại
            }
        }
        return loginSuccess;
    }
}
