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
                // Чекаємо на нове підключення
                Socket clientSocket = serverSocket.accept();

                // Для кожного клієнта створюємо окремий потік
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
                // 1. Читаємо команду від клієнта через ваш NetworkCodec
                String request = NetworkCodec.read(in);

                // 2. Обробляємо запит
                String response = processRequest(request);

                // 3. Відправляємо результат назад клієнту
                NetworkCodec.write(out, response);
            }
        } catch (IOException e) {
            System.out.println("Клієнт відключився.");
        }
    }

    private String processRequest(String request) {
        // Тут ви перевіряєте, що хоче клієнт. Наприклад:
        if (request.equals("GET_ALL_STUDENTS")) {
            // Викликаєте ваш сервіс і повертаєте результат (можна в JSON)
            return service.getStudentService().findAll().toString();
        }
        return "Невідома команда";
    }
