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

    public ClientHandler(Socket clientSocket, ClientHandlerDisconnected callback) throws IOException {
        this.callback = callback;
        this.clientSocket = clientSocket;
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),StandardCharsets.UTF_8));
        output = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void actionGetData() throws IOException, SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        try {
            databaseHelper.connect();
            ResultSet resultSet = databaseHelper.selectData("congviec");
            List<CongViec> congViecList = CongViec.resultSetToList(resultSet);
            Gson gson = new Gson();
            String gsonData = gson.toJson(congViecList);
            output.writeBytes(gsonData+"\n");
        } catch (SQLException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (databaseHelper.getConnection() != null) { // Kiểm tra xem kết nối có tồn tại không trước khi đóng
                databaseHelper.disconnect(); // Đóng kết nối nếu tồn tại
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
                output.writeBytes("0\n");
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
                String[] login = s.split("\\|");
                if (login[0].equals("0")) {
                    checkLogin(login);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            callback.onClientDisconnected(this);
        }
    }

}
