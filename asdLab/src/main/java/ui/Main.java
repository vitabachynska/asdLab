package ui;

import domain.*;
import service.Authorization;
import service.Service;
import service.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static service.Authorization.RoleForm.MANAGER;

public class Main {
    private static University university;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Service service = new Service();
        Validation validation = new Validation();
        validation.initData();
        while (true) {
            validation.introduction();
            //password = 1234
            while (true) {
                validation.menu();
                int choice;

                while (true) {
                    System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
                    System.out.println("====* натисність відповідну клавішу *====");
                    String input = scanner.nextLine();
                    try {
                        choice = Integer.parseInt(input);
                        if (choice >= 0 && choice <= 5)
                            break;
                        else
                            System.out.println("ПОМИЛКА. Введіть число зі списку : ");

                    } catch (NumberFormatException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }
                if (choice == 0) {
                    System.out.println("==ВИХІД З ПРОГРАМИ==");
                    break;
                }

                switch (choice) {
                    case 1 -> {
                        validation.workWithUni();
                    }
                    case 2 -> {
                        validation.workWithFaculty();
                    }
                    case 3 -> {
                        validation.workWithDepartment();
                    }
                    case 4 -> {
                        validation.workWithTeachers();
                    }
                    case 5 -> {
                        validation.workWithStudents();
                    }
                }

                while (true) {
                    System.out.println("- - - - - - - - - - - -\nБажаєте повернутися до меню? 1 - так, 0 - ні : ");
                    String input = scanner.nextLine();
                    try {
                        choice = Integer.parseInt(input);
                        if (choice == 1 || choice == 0)
                            break;
                        else
                            System.out.println("Введіть коректне значення");

                    } catch (NumberFormatException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }
                if (choice == 0) {
                    System.out.println("--ВИХІД З ПРОГРАМИ--");
                    break;
                }
            }
            int choice;
            while (true) {
                System.out.println("- - - - - - - - - - - -\nБажаєте повернутися до меню? 1 - так, 0 - ні : ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice == 1 || choice == 0)
                        break;
                    else
                        System.out.println("Введіть коректне значення");

                } catch (NumberFormatException e) {
                    System.out.println("Введіть коректне значення");
                }
            }
            if (choice == 0) {
                System.out.println("--ВИХІД З ПРОГРАМИ--");
                break;
            }
        }
    }
    private static String textIsNotNull(){
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





    }

