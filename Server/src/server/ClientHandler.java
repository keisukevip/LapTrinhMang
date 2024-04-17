package server;

import com.google.gson.Gson;
import com.mysql.cj.xdevapi.Result;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;
    private DataOutputStream output;
    private String username;
    private Boolean check = false;
    private ClientHandlerDisconnected callback;
    private ClientHandler clientHandler;
    private DatabaseHelper databaseHelper;

    public ClientHandler(Socket clientSocket, ClientHandlerDisconnected callback, DatabaseHelper databaseHelper) throws IOException {
        this.databaseHelper = databaseHelper;
        this.callback = callback;
        this.clientSocket = clientSocket;
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        output = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void addData(String input) throws SQLException {
        Gson gson = new Gson();
        CongViec gsonData = gson.fromJson(input, CongViec.class);
        databaseHelper.connect();
        String tenCongViec = gsonData.getTenCongViec();
        String nguoiLam = gsonData.getNguoiThucHien();
        String trangThai = gsonData.getTrangThai();
        databaseHelper.insertData("CongViec", new String[]{"tenCongViec", "nguoiThucHien", "trangThai"}, new Object[]{tenCongViec, nguoiLam, trangThai});
        System.out.println("Thêm công việc thành công");
        databaseHelper.disconnect();
        broadCast();
    }

    public void deletaData(String input) throws SQLException {
        databaseHelper.connect();
        databaseHelper.deleteData("CongViec", "id = " + input);
        databaseHelper.disconnect();
        broadCast();
    }

    public void updateDataApprove(String input) throws SQLException {
        Gson gson = new Gson();
        CongViec gsonData = gson.fromJson(input, CongViec.class);
        databaseHelper.connect();
        ResultSet selectRow = databaseHelper.selectRow("CongViec", "id = " + gsonData.getId());
        selectRow.next();
        int id = selectRow.getInt("id"); // Giả sử cột đầu tiên là cột ID
        String tenCongViec = selectRow.getString("tenCongViec"); // Giả sử cột thứ hai là cột Tên công việc
        String nguoiLam = selectRow.getString("nguoiThucHien");
        String trangThai = selectRow.getString("trangThai");
        String version = selectRow.getString("version");
        System.out.println("==================");
        System.out.println(tenCongViec + " " + nguoiLam + " " + trangThai);
        System.out.println(input);
        System.out.println("==================");
        if (trangThai.equals("PROCESSING") && !nguoiLam.equals(gsonData.getNguoiThucHien())) {
            System.out.println(nguoiLam + "====" + gsonData.getNguoiThucHien());
            System.out.println("PROCESSING trong updateDataApprove");
            return;
        }
        if (gsonData.getTrangThai().equals("PENDING")) {
            // Cập nhật dữ liệu và tăng phiên bản lên 1
            databaseHelper.updateData("CongViec", new String[]{"nguoiThucHien", "trangThai", "version"}, new Object[]{"", gsonData.getTrangThai(), Integer.parseInt(version) + 1}, "id = " + gsonData.getId() + " and version = " + version);
        } else {
            // Cập nhật dữ liệu và tăng phiên bản lên 1
            databaseHelper.updateData("CongViec", new String[]{"nguoiThucHien", "trangThai", "version"}, new Object[]{gsonData.getNguoiThucHien(), gsonData.getTrangThai(), Integer.parseInt(version) + 1}, "id = " + gsonData.getId() + " and version = " + version);
        }
        databaseHelper.disconnect();
        broadCast();

    }

    public void broadCast() throws SQLException {
        Gson gson = new Gson();
        databaseHelper.connect();
        ResultSet resultSet = databaseHelper.selectData("CongViec");
        List<CongViec> congViecList = CongViec.resultSetToList(resultSet);
        String gsonData2 = gson.toJson(congViecList);
        ExecutorService executorService = Executors.newFixedThreadPool(Server.clientHandlers.size());
        for (ClientHandler clientHandler : Server.clientHandlers) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        clientHandler.sendData("1|" + gsonData2 + "\n");
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
        }
        databaseHelper.disconnect();
    }

    public void sendData(String message) throws IOException {
        output.writeBytes(message);
    }

    public void actionGetData() throws IOException, SQLException {
        ResultSet resultSet = null;
        try {
            databaseHelper.connect();
            resultSet = databaseHelper.selectData("congviec");
            List<CongViec> congViecList = CongViec.resultSetToList(resultSet);
            Gson gson = new Gson();
            String gsonData = gson.toJson(congViecList);
            output.writeBytes("0|" + gsonData + "\n");
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                resultSet.close(); // Đóng ResultSet sau khi sử dụng
            }
            if (databaseHelper.getConnection() != null) {
                databaseHelper.disconnect(); // Đảm bảo đóng kết nối sau khi sử dụng
            }
        }
    }

    public void checkLogin(String[] login) throws IOException {
        try {
            System.out.println(login[1]);
            Gson gson = new Gson();
            TaiKhoan taiKhoan = gson.fromJson(login[1], TaiKhoan.class);
            if (taiKhoan.kiemTraDangNhap()) {
                check = true;
                username = taiKhoan.getTenDangNhap();
            }
            if (check) {
                actionGetData();
            } else {
                output.writeBytes("0|false\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String s = input.readLine();
                System.out.println(s);
                String[] input = s.split("\\|");
                if (input[0].equals("0")) {
                    checkLogin(input);
                } else if (input[0].equals("1")) {
                    updateDataApprove(input[1]);
                } else if (input[0].equals("2")) {
                    addData(input[1]);
                } else {
                    deletaData(input[1]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            callback.onClientDisconnected(this);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
