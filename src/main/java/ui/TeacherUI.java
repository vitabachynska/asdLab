package ui;

import service.NetworkCodec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class TeacherUI {
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Scanner scanner;

    // Залишаємо тільки мережевий конструктор
    public TeacherUI(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
        this.scanner = new Scanner(System.in);
    }

    public void workWithTeachers() throws IOException {
        while (true) {
            printMenu();
            System.out.print("==== ОБЕРІТЬ ПУНКТ ==== ");
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
                continue;
            }

            if (choice == 0) break;

            switch (choice) {
                case 1 -> showTeachers();
                case 2 -> updateTeacher();
                case 3 -> searchTeacherByPIB();
                default -> System.out.println("Некоректний вибір.");
            }
        }
    }

    private void showTeachers() throws IOException {
        System.out.println("\n--------ВИВІД СПИСКУ ВИКЛАДАЧІВ--------");
        NetworkCodec.write(out, "GET_ALL_TEACHERS");

        String response = NetworkCodec.read(in);
        System.out.println(response);
    }

    private void updateTeacher() throws IOException {
        System.out.println("\n--------ЗМІНЮЄМО ДАНІ ВИКЛАДАЧА--------");
        System.out.print("Введіть ID викладача для оновлення: ");
        String id = scanner.nextLine();

        System.out.print("Нове ім'я: ");
        String name = scanner.nextLine();

        String command = "UPDATE_TEACHER:" + id + ":" + name;
        NetworkCodec.write(out, command);

        String response = NetworkCodec.read(in);
        System.out.println(response);
    }

    private void searchTeacherByPIB() throws IOException {
        System.out.println("\n--------ПОШУК ВИКЛАДАЧА ЗА ПІБ--------");
        System.out.print("Введіть Прізвище: ");
        String lastName = scanner.nextLine();

        NetworkCodec.write(out, "SEARCH_TEACHER:" + lastName);

        String response = NetworkCodec.read(in);
        System.out.println(response);
    }

    private void printMenu() {
        System.out.println("\n====================== РОБОТА З ВИКЛАДАЧАМИ ======================");
        System.out.println("1. Показати викладачів");
        System.out.println("2. Змінити інформацію про викладача");
        System.out.println("3. Пошук викладача за ПІБ");
        System.out.println("0. Вийти в головне меню");
    }
}