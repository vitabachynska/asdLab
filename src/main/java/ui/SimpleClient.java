package ui;

import service.NetworkCodec;
import service.Validation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String message = "Привіт, Сервер!";
            System.out.println("Відправляю: " + message);

            NetworkCodec.write(out, message);

            String response = NetworkCodec.read(in);
            System.out.println("Сервер відповів: " + response);

        } catch (IOException e) {
            System.err.println("Помилка: Переконайтеся, що СЕРВЕР запущено першим!");
        }
    }
}
