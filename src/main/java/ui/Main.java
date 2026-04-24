package ui;

import domain.*;
import exceptions.*;
import service.Service;
import service.Validation;

import java.util.Scanner;

public class Main {
    private static University university;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service service = new Service();
        Validation validation = new Validation(service);
        UniversityUI universityUI = new UniversityUI(service);
        FacultyUI facultyUI = new FacultyUI(service);
        DepartmentsUI departmentsUI = new DepartmentsUI(service);
        TeacherUI teacherUI = new TeacherUI(service);
        StudentUI studentUI = new StudentUI(service);
        validation.initData();

        try {
            service.startup();
        } catch (DataPersistenceException e) {
            System.out.println("Помилка: не можна завантажити дані: " + e.getMessage());
        } catch (UniversityException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        while (true) {
            validation.introduction();
            //meneger password = 1234
            //admin password =  4321
            while (true) {
                validation.menu();
                int choice;

                while (true) {
                    System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
                    System.out.println("\n====* натисність відповідну клавішу *====");
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
                        universityUI.workWithUniversity();
                    }
                    case 2 -> {
                        facultyUI.workWithFaculty();
                    }
                    case 3 -> {
                        departmentsUI.workWithDepartment();
                    }
                    case 4 -> {
                        teacherUI.workWithTeachers();
                    }
                    case 5 -> {
                        studentUI.workWithStudents();
                    }
                    case 6 -> {
                        validation.adminControlPanel();
                    }
//                case 7 -> {
//                    try {
//                        service.syncWithFile();
//                        System.out.println("Дані збережені у файл!");
//                    } catch (DataPersistenceException e) {
//                        System.out.println("Помилка: " + e.getMessage());
//                    }
//                }
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
            //  }
        }


    }
}
