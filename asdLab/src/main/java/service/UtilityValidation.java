package service;

import domain.University;

import java.util.Scanner;

public class UtilityValidation {
    static Scanner scanner = new Scanner(System.in);

    static Service service = new Service();


    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) return val;
                System.out.printf("Помилка. Введіть число від %d до %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне числове значення");
            }
        }
    }

    public static String askInput(String message) {
        System.out.print(message);
        return textIsNotNull();
    }

    public static String textIsNotNull() {
        String text;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            text = scanner.nextLine();
            if (text == null || text.isEmpty())
                System.out.println("Введіть значення : ");
            else break;
        }
        return text;
    }

    public static boolean isUniversityExist() {
        University currentUni = service.getUniversity();
        if (currentUni == null) {
            System.out.println("Спочатку створіть університет ");
            return false;
        }

        return true;
    }
}
