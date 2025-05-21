package com.ijse.gdse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server Starting...");
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected!");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Thread to read messages from client
            Thread reader = new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        System.out.println("Client: " + message);
                    } catch (IOException e) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                }
            });

            // Thread to send messages to client
            Thread writer = new Thread(() -> {
                try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (true) {
                        String message = consoleReader.readLine();
                        out.writeUTF(message);
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            reader.start();
            writer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
