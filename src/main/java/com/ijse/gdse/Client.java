package com.ijse.gdse;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to Server!");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Thread to read messages from server
            Thread reader = new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        System.out.println("Server: " + message);
                    } catch (IOException e) {
                        System.out.println("Server disconnected.");
                        break;
                    }
                }
            });

            // Thread to send messages to server
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
