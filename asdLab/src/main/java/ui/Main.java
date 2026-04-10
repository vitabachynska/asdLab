package ui;

import domain.*;
import service.RoleForm;
import service.UniversityService;
import service.Validation;

import java.util.Scanner;

public class Main {
    private static University university;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Service service = new Service();
        Validation validation = new Validation();
        UniversityService universityService = new UniversityService();
        validation.initData();
        while (true) {
            validation.introduction();
            //meneger password = 1234
            //admin password =  4321
            while (true) {
                validation.menu();
                int choice;

                while (true) {
                    System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
                    System.out.println("====* натисність відповідну клавішу *====");
                    String input = scanner.nextLine();
                    try {
                        choice = Integer.parseInt(input);
                        if (choice >= 0 && choice <= 6)
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
                        universityService.workWithUniversity();
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
                    case 6 -> {
                        validation.adminControlPanel();
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
//            int choice;
//            while (true) {
//                System.out.println("- - - - - - - - - - - -\nБажаєте повернутися до меню? 1 - так, 0 - ні : ");
//                String input = scanner.nextLine();
//                try {
//                    choice = Integer.parseInt(input);
//                    if (choice == 1 || choice == 0)
//                        break;
//                    else
//                        System.out.println("Введіть коректне значення");
//
//                } catch (NumberFormatException e) {
//                    System.out.println("Введіть коректне значення");
//                }
//            }
//            if (choice == 0) {
//                System.out.println("--ВИХІД З ПРОГРАМИ--");
//                break;
//            }
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

