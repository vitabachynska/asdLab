package ui;

import exceptions.*;
import service.*;

public class UniversityUI {
    private Service service;

    public UniversityUI(Service service) {
        this.service = service;
    }

    public void workWithUniversity() {
        try {
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
        }}catch (UniversityException e){
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void createUniversity() {
        if (!Authorization.can(RoleForm.MANAGER.getMask()) && !Validation.hasRights) {
            System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них");
            return;
        }
        System.out.println("\n--------ОНОВЛЕННЯ УНІВЕРСИТЕТУ--------");
        try {
            String fullName = UtilityValidation.askInput("Введіть назву університету: ");
            String shortName = UtilityValidation.askInput("Введіть скорочену назву університету: ");
            String city = UtilityValidation.askInput("Введіть місто: ");
            String address = UtilityValidation.askInput("Введіть адресу: ");
            service.addUniversity(fullName, shortName, city, address);
            service.syncWithFile();
            System.out.println("УНІВЕРСИТЕТ УСПІШНО ОНОВЛЕНО");

        } catch (AuthorizationException e) {
            System.out.println("Відмовлено в доступі: " + e.getMessage());
        } catch (DataPersistenceException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private void showUniversity() {
        System.out.println("\n--------УНІВЕРСИТЕТ--------");
        var university = this.service.getUniversity();
        if (university == null) {
            System.out.println("Університет поки не зареєстровано :(");
        } else {
            System.out.println(university);
        }
    }

    private void printUniMenu() {
        System.out.println("\n======================РОБОТА З УНІВЕРСИТЕТОМ======================");
        System.out.println("1. оновити університет\n2. показати університет\n0. вийти\n");
    }


}
