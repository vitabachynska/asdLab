package service;

import domain.*;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Validation {
    Service service;
    InmemoryStudents inmemoryStudents = new InmemoryStudents();
    InmemoryTeachers inmemoryTeachers = new InmemoryTeachers();
    Scanner scanner = new Scanner(System.in);
    public static boolean hasRights;

    //public Validation(Service service) {
        //this.service = service;
    //}

    public Validation(DataInputStream in, DataOutputStream out) {
    }

    public void initData() {
        hasRights = true;
        University university = new University("Національний університет «Києво-Могилянська академія»", "НаУКМА", "Київ", "1");
        service.addUniversity(university.getFullName(), university.getShortName(), university.getCity(), university.getAddress());
        hasRights = false;

        System.out.println("Тестові дані успішно завантажено");

    }

    public void adminControlPanel() {

        if (!Authorization.can(RoleForm.ADMIN.getMask())) {
            System.out.println("Доступ заборонено. Потрібні права Адміністратора");
            return;
        }

        System.out.println("\n--- ПАНЕЛЬ КЕРУВАННЯ ДОСТУПОМ ---");
        //System.out.println("1. Заблокувати всі операції редагування (для Менеджерів)");
        //System.out.println("2. Надати доступ (Розблокувати)");
        System.out.println("1. Змінити пароль (менеджери)");
        System.out.println("2. Змінити пароль (адміністратори)");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.print("Введіть новий пароль для Менеджера: ");
            Authorization.setManagerPassword(scanner.nextLine());
        } else {
            System.out.print("Введіть новий пароль для Адміністратора: ");
            Authorization.setAdminPassword(scanner.nextLine());
        }
    }



    private String textIsNotNull(){
        String text;
        Scanner scanner = new Scanner(System.in);
        while (true){
            text = scanner.nextLine();
            if(text == null || text.isEmpty())
                System.out.println("Введіть значення : ");
            else break;
        }
        return text;
    }




    public void introduction() {
        String input = "";
        System.out.println("\n\n======================СИСТЕМА 'DigiUni'!======================"+
                "\nСистема обліку студентів і викладачів\n");
        boolean authenticated = false;

        System.out.println("=== ВІТАЄМО В СИСТЕМІ УНІВЕРСИТЕТУ ===");

        while (!authenticated) {
            System.out.println("\nОберіть вашу роль для входу:");
            System.out.println("1. Гість (Тільки перегляд)");
            System.out.println("2. Менеджер (Потрібен пароль)");
            System.out.println("3. Адміністратор (Потрібен пароль)");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    Authorization.loginAsUser();
                    authenticated = true;
                }
                case "2" -> {
                    System.out.print("Введіть пароль менеджера: ");
                    String pass = scanner.nextLine();
                    if (Authorization.loginAsManager(pass)) {
                        authenticated = true;
                    } else {
                        System.out.println("Спробуйте ще раз");
                    }
                }
                case "3" -> {
                    System.out.print("Введіть пароль адміністратора: ");
                    String pass = scanner.nextLine();
                    if (Authorization.loginAsAdmin(pass)) {
                        authenticated = true;
                    } else {
                        System.out.println("Спробуйте ще раз");
                    }
                }
                default -> System.out.println("Некоректний вибір.");
            }
        }

    }
    public void menu(){
        System.out.println("\n\n======================\nВАС ВІТАЄ СИСТЕМА 'DigiUni'!\n======================"+
            "\nнижче представлене МЕНЮ, в якому можна створити універсистет, додавати спеціальності, "+
            "студентів та викладачів.\nІєрархія побудована наступним чином :\n\n"+
            "УНІВЕРСИТЕТ -> ФАКУЛЬТЕТИ -> КАФЕДРИ -> ВИКЛАДАЧІ і СТУДЕНТИ"+
            "\n\n");

        System.out.println("======================\nМЕНЮ\n======================");
        System.out.println("1. робота з УНІВЕРСИТЕТОМ ======================\n");
        System.out.println("2. робота з ФАКУЛЬТЕТОМ ======================\n");
        System.out.println("3. робота з КАФЕДРОЮ ======================\n");
        System.out.println("4. робота з ВИКЛАДАЧАМИ ======================\n");
        System.out.println("5. робота зі СТУДЕНТАМИ ======================\n");
        //System.out.println("6. завантажити дані ======================\n");
        System.out.println("6. робота з ДОСТУПОМ ======================\n");
        System.out.println("0. вийти з програми");
    }


}
