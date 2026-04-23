package ui;

import service.NetworkCodec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5555)) {
            System.out.println("--- СЕРВЕР ЗАПУЩЕНО (чекаю підключення) ---");

            try (Socket socket = server.accept();
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                System.out.println("Клієнт підключився!");

                String request = NetworkCodec.read(in);
                System.out.println("Отримано від клієнта: " + request);

                String response = "Ехо-відповідь: " + request;
                NetworkCodec.write(out, response);
                System.out.println("Відповідь надіслано.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
