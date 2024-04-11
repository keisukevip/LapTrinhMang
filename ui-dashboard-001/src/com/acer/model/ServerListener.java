package com.acer.model;

import com.acer.event.ServerMessageListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener extends Thread {

    private BufferedReader inputStream;
    private boolean keepListening = true;
    private final Object lock = new Object(); // Khóa đồng bộ hóa
    private List<ServerMessageListener> listeners = new ArrayList<>();

    public ServerListener(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public void addServerMessageListener(ServerMessageListener listener) {
        synchronized (lock) {
            listeners.add(listener);
            System.out.println(listeners.size());
        }
    }

    public void removeServerMessageListener(ServerMessageListener listener) {
        synchronized (lock) {
            listeners.remove(listener);
        }
    }

    private void notifyMessageReceived(String message) {
        List<ServerMessageListener> copyListeners;
        synchronized (lock) {
            copyListeners = new ArrayList<>(listeners); // Tạo một bản sao của listeners để tránh ConcurrentModificationException
        }
        for (ServerMessageListener listener : copyListeners) {
            listener.onMessageReceived(message);
        }
    }

    @Override
    public void run() {
        while (keepListening) {
            try {
                String s = inputStream.readLine();
                notifyMessageReceived(s);
            } catch (IOException ex) {
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

