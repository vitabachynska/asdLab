package ui;

import service.NetworkCodec;
import service.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UniversityServer {
    private final Service service; // Ваша бізнес-логіка

    public UniversityServer(Service service) {
        this.service = service;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер НаУКМА запущено на порту " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                String request = NetworkCodec.read(in);
                String response = processRequest(request);
                NetworkCodec.write(out, response);
            }
        } catch (IOException e) {
            System.out.println("Клієнт відключився.");
        }
    }

    private String processRequest(String request) {
        if (request.equals("GET_ALL_STUDENTS")) {

            return service.studentService.findAll("").toString();
        }
        return "Невідома команда";
    }
}