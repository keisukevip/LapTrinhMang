package com.acer.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSocket {

    private Socket clientSocket;
    private BufferedReader input;
    private DataOutputStream output;

    public ClientSocket() throws IOException {
        this.clientSocket = new Socket("127.0.0.1", 8080);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),StandardCharsets.UTF_8));
        output = new DataOutputStream(clientSocket.getOutputStream());
    }

    public BufferedReader getInput() {
        return input;
    }
    
    

    public void sendData(String data) throws IOException {
        output.writeBytes(data);
    }

    public String receiveData() throws IOException {
        return input.readLine();
    }

    public void closeConnection() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("Error while closing connection.");
        }
    }
}
