package service;

import domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UtilityValidation {
    static Scanner scanner = new Scanner(System.in);

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

    public static boolean isUniversityExist(Service service) {
        University currentUni = service.getUniversity();
        if (currentUni == null) {
            System.out.println("Спочатку створіть університет ");
            return false;
        }

        return true;
    }

    public static LocalDate readDate(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(UtilityValidation.askInput(prompt));
            } catch (DateTimeParseException e) {
                System.out.println("Формат дати: РРРР-ММ-ДД");
            }
        }
    }

    public static <T extends Enum<T>> T readEnum(Class<T> enumClass, String prompt) {
        while (true) {
            try {
                String input = UtilityValidation.askInput(prompt);
                if (enumClass == Teacher.TeachersPosition.class) return (T) Teacher.TeachersPosition.fromString(input);
                if (enumClass == Teacher.TeachersDegree.class) return (T) Teacher.TeachersDegree.fromString(input);
                if (enumClass == Teacher.TeachersAcademicTitle.class) return (T) Teacher.TeachersAcademicTitle.fromString(input);
                if (enumClass == Student.TuitionForm.class) return (T) Student.TuitionForm.fromString(input);
                if (enumClass == Student.StudentStatus.class) return (T) Student.StudentStatus.fromString(input);
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (Exception e) {
                System.out.println("Некоректне значення!");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(UtilityValidation.askInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Введіть число!");
            }
        }
    }

    public static int readYear(String prompt) {
        while (true) {
            int year = UtilityValidation.readInt(prompt, 1900, LocalDate.now().getYear());
            if (year <= LocalDate.now().getYear()) return year;
            System.out.println("Рік введено некоректно!");
        }
    }

    public static boolean checkRights() {
        if (!Authorization.can(RoleForm.MANAGER.getMask()) && !Validation.hasRights) {
            System.out.println("Помилка: Потрібні права менеджера");
            return false;
        }
        return true;
    }



}

