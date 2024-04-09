package server;


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

    // Getters and setters (not shown for brevity)
    // Phương thức chuyển đổi ResultSet thành danh sách các đối tượng CongViec
    public static List<CongViec> resultSetToList(ResultSet resultSet) throws SQLException {
        List<CongViec> congViecList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String tenCongViec = resultSet.getString("tenCongViec");
            String nguoiThucHien = resultSet.getString("nguoiThucHien");
            String trangThai = resultSet.getString("trangThai");
            CongViec congViec = new CongViec(id, tenCongViec, nguoiThucHien, trangThai);
            congViecList.add(congViec);
        }
        return congViecList;
    }
}
