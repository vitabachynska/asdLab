package ui;

import service.NetworkCodec;
import service.Validation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 5555)) {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Validation validation = new Validation(in, out);
            UniversityUI universityUI = new UniversityUI(in, out);
            FacultyUI facultyUI = new FacultyUI(in, out);
            DepartmentsUI departmentsUI = new DepartmentsUI(in, out);
            TeacherUI teacherUI = new TeacherUI(in, out);
            StudentUI studentUI = new StudentUI(in, out);
            // Твої UI класи тепер мають отримувати потоки (in/out)
            // замість прямого об'єкта service
            Validation validation = new Validation(in, out);

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
            }
                // ... весь твій цикл зі switch (choice) йде сюди ...
            } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    } catch (IOException e) {
            System.out.println("Сервер не доступний!");
        }
    }
}
