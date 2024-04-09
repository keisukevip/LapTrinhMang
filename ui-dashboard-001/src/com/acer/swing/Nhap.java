//package server;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class Server {
//
//    private static final List<ClientHandler> clientHandlers = Collections.synchronizedList(new ArrayList<>());
//
//    public static void main(String[] args) throws IOException {
//        ServerSocket welcomeSocket = new ServerSocket(8080);
//        System.out.println("Server is running ...");
//
//        while (true) {
//            Socket clientSock = welcomeSocket.accept();
//            ClientHandler clientHandler = new ClientHandler(clientSock, clientHandlers::remove);
//            clientHandlers.add(clientHandler);
//            System.out.println(clientHandlers.size());
//            Thread thread = new Thread(clientHandler);
//            thread.start();
//        }
//    }
//
//    // Định nghĩa interface đây, nếu nó chưa được định nghĩa ở đâu đó trong code.
//    public interface ClientHandlerDisconnected {
//        void onClientDisconnected(ClientHandler clientHandler);
//    }
//}