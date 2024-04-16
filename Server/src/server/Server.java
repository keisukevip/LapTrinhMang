package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static final List<ClientHandler> clientHandlers = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(8080);
        System.out.println("Server is running ...");
        

        while (true) {
            DatabaseHelper databaseHelper = new DatabaseHelper();
            Socket clientSock = welcomeSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSock, new ClientHandlerDisconnected() {
                @Override
                public void onClientDisconnected(ClientHandler clientHandler) {
                    clientHandlers.remove(clientHandler);
                    System.out.println(clientHandlers.size());
                }
            }, databaseHelper);
            // Thêm client socket mới vào danh sách.
            clientHandlers.add(clientHandler);
            System.out.println(clientHandlers.size());
            Thread thread = new Thread(clientHandler);
            thread.start();
//        }
        }

    }
}