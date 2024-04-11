package server;

import java.sql.*;

public class DatabaseHelper {

    private Connection connection;
    private String url;
    private String username;
    private String password;

    public DatabaseHelper() {
        this.url = "jdbc:mysql://localhost:3306/QuanLyCongViec";
        this.username = "root";
        this.password = "";
    }

    // Phương thức kết nối đến cơ sở dữ liệu
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    // Phương thức đóng kết nối đến cơ sở dữ liệu
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Phương thức thêm dữ liệu vào bảng
    public void insertData(String tableName, String[] columns, Object[] values) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(tableName).append(" (");

        for (int i = 0; i < columns.length; i++) {
            queryBuilder.append(columns[i]);
            if (i < columns.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(") VALUES (");

        for (int i = 0; i < values.length; i++) {
            queryBuilder.append("?");
            if (i < values.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
        preparedStatement.executeUpdate();
    }

    // Phương thức cập nhật dữ liệu trong bảng
    public void updateData(String tableName, String[] columns, Object[] values, String condition) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("UPDATE ");
        queryBuilder.append(tableName).append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            queryBuilder.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(" WHERE ").append(condition);

        PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString());
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
        preparedStatement.executeUpdate();
    }

    // Phương thức xóa dữ liệu từ bảng
    public void deleteData(String tableName, String condition) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + condition;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public ResultSet selectRow(String tableName, String condition) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + condition;
        System.out.println(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.executeQuery();
    }

    // Phương thức đọc dữ liệu từ bảng
    public ResultSet selectData(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public Connection getConnection() {
        return connection;
    }
}
