package service;

import domain.*;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;

import java.util.Scanner;

public class Validation {
    Service service;
    InmemoryStudents inmemoryStudents = new InmemoryStudents();
    InmemoryTeachers inmemoryTeachers = new InmemoryTeachers();
    Scanner scanner = new Scanner(System.in);
    public static boolean hasRights;

    public Validation(Service service) {
        this.service = service;
    }

    public void initData() {
        boolean isLoaded = service.loadUniversityFromStorage();
        System.out.println("Програма шукає файл тут: " + new java.io.File(".").getAbsolutePath());

        if (isLoaded) {

            System.out.println("Дані успішно відновлено з файлу.");
        } else {
            hasRights = true;
            University university = new University("Національний університет «Києво-Могилянська академія»", "НаУКМА", "Київ", "2");
            service.addUniversity(university.getFullName(), university.getShortName(), university.getCity(), university.getAddress());
            hasRights = false;

            System.out.println("Файл даних не знайдено або він порожній. Завантажую дефолтні значення");

        }

    }

    public void adminControlPanel() {
        if (!Authorization.can(RoleForm.ADMIN)) {
            System.out.println("!!! Доступ заборонено. Потрібні права Адміністратора !!!");
            return;
        }

        System.out.println("\n--- ПАНЕЛЬ КЕРУВАННЯ ДОСТУПОМ ---");
        //System.out.println("1. Заблокування/розблокування (для Менеджерів)");
        //System.out.println("2. Надати доступ (Розблокувати)");
        System.out.println("1. Заблокувати всі операції редагування (для Менеджерів)");
        System.out.println("2. Надати доступ (Розблокувати)");

        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            Validation.hasRights = false;
            System.out.println("Доступ для менеджерів обмежено.");
        } else {
            Validation.hasRights = true;
            System.out.println("Доступ відновлено.");
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
        RoleForm newRoleForm = null;
        while ( newRoleForm == null) {
            System.out.println("Введіть роль (Користувач/Менеджер/Адміністратор) : ");
            input = scanner.nextLine();
            try {
                newRoleForm = RoleForm.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Введіть форму з поданих");
            }
        }
        RoleForm selectedRole = RoleForm.fromString(input);

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        if (selectedRole == RoleForm.ADMINISTRATOR) {
            //System.out.print("Введіть логін: ");
            //String login = scanner.nextLine();
            //System.out.print("Введіть пароль: ");
            //String password = scanner.nextLine();

            Authorization.loginAsAdmin(password);}
        else if (selectedRole == RoleForm.MANAGER) {
            //System.out.print("Введіть логін: ");
            //String login = scanner.nextLine();
            //System.out.print("Введіть пароль: ");
            //String password = scanner.nextLine();

            Authorization.loginAsManager(password);
        } else {
            //System.out.print("Введіть логін: ");
            //String login = scanner.nextLine();
            Authorization.loginAsUser();
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
        System.out.println("6. завантажити дані ======================\n");
        System.out.println("7. робота з ДОСТУПОМ ======================\n");
        System.out.println("0. вийти з програми");
    }


}
