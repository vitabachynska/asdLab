package ui;

import service.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UniversityUI {
    private final DataInputStream in;
    private final DataOutputStream out;

    public UniversityUI(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void workWithUniversity() throws IOException {
        printUniMenu();
        while (true) {
            int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 2);
            switch (choice) {
                case 1 -> createUniversity();
                case 2 -> showUniversity();
                case 0 -> {
                    System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
                    return;
                }
            }
            printUniMenu();
        }
    }

    private void createUniversity() throws IOException {
        // Перевірка Authorization.can(RoleForm.MANAGER...) має бути на СЕРВЕРІ
        System.out.println("\n--------СТВОРЕННЯ УНІВЕРСИТЕТУ--------");
        String fullName = UtilityValidation.askInput("Введіть назву університету: ");
        String shortName = UtilityValidation.askInput("Введіть скорочену назву університету: ");
        String city = UtilityValidation.askInput("Введіть місто: ");
        String address = UtilityValidation.askInput("Введіть адресу: ");

        NetworkCodec.write(out, "CREATE_UNIVERSITY:" + fullName + ";" + shortName + ";" + city + ";" + address);

        System.out.println(NetworkCodec.read(in));
    }

    private void showUniversity() throws IOException {
        System.out.println("\n--------УНІВЕРСИТЕТ--------");

        NetworkCodec.write(out, "GET_UNIVERSITY");

        String response = NetworkCodec.read(in);
        System.out.println(response);
    }

    private void printUniMenu() {
        System.out.println("\n======================РОБОТА З УНІВЕРСИТЕТОМ======================");
        System.out.println("1. створити університет\n2. показати університети\n0. вийти\n");
    }
}
