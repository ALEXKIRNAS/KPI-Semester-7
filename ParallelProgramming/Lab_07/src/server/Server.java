package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Server extends Thread {

    private HashSet<User> users;
    private ArrayList<ClientServiceThread> serviceThreads;

    public void run() {
        users = new HashSet<>();
        serviceThreads = new ArrayList<>();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(2123);
        } catch (IOException e) {
            throw new RuntimeException("Can't create server socket");
        }

        while (!isInterrupted()) {

            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            ClientServiceThread clientServiceThread = new ClientServiceThread(this, socket);
            serviceThreads.add(clientServiceThread);
            clientServiceThread.start();
        }
    }

    public void logInUser(User u) {
        users.add(u);

    }
}
