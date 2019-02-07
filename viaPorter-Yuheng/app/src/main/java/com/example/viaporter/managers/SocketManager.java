package com.example.viaporter.managers;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;

public class SocketManager {
    private static final String TAG = "SocketManager";

    // private properties
    private Socket socket;
    private Gson gson;

    public Socket getSocket() {
        if (socket == null) {
            synchronized (this) {
                connectSocket();
            }
        }
        return socket;
    }

    public SocketManager() {
        gson = new Gson();
    }

    public void connectSocket() {
        try {
            socket = IO.socket("http://172.25.106.233:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void disconnectSocket() {
        getSocket().disconnect();
    }

    public void reconnectSocket() {
        // disconnect old socket
        if (socket != null) {
            socket.disconnect();
            socket.connect();
        }
    }

    public void emitString(String event, String msg) {
        getSocket().emit(event, msg);
    }
}
