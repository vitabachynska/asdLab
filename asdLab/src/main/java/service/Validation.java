package service;

import domain.*;
import repository.InmemoryStudents;
import repository.InmemoryTeachers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Validation {
    Service service = new Service();
    InmemoryStudents inmemoryStudents = new InmemoryStudents();
    InmemoryTeachers inmemoryTeachers = new InmemoryTeachers();
    Scanner scanner = new Scanner(System.in);

    public void workWithUni(){//=========================================================================================================================================
        System.out.println("======================РОБОТА З УНІВЕРСИТЕТОМ======================");
        System.out.println("1. створити університет\n2. показати університети\n0. вийти\n");
        int choice1;
        while (true) {
            System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
            String input = scanner.nextLine();
            try {
                choice1 = Integer.parseInt(input);
                if (choice1 >= 0 && choice1 <= 2)
                    break;
                else
                    System.out.println("ПОМИЛКА. Введіть число зі списку : ");

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (choice1 == 0) {
            System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
            introduction();}

        switch (choice1){
            case 1 -> {
                System.out.println("\n--------СТВОРЕННЯ УНІВЕРСИТЕТУ--------");
                System.out.println("Введіть назву університету : ");
                String fullName = textIsNotNull();
                System.out.println("Введіть скорочену назву університету : ");
                String shortName = textIsNotNull();
                System.out.println("Введіть місто знаходження університету : ");
                String city = textIsNotNull();
                System.out.println("Введіть адресу університету : ");
                String address = textIsNotNull();
                service.addUniversity(fullName, shortName, city, address);
                //university = new University(fullName, shortName, city, address);
                System.out.println("УНІВЕРСИТЕТ БУЛО ДОДАНО");
            }
            //   case 2 -> {
                //видалення університету
            //  }
            case 2 -> {
                System.out.println("\n--------УНІВЕРСИТЕТ--------");
                if (service.getUniversity() == null) {
                    System.out.println("Університет поки не зареєстровано :(");
                } else {
                    System.out.println(service.getUniversity().toString());
                }
            }
        }
    }

    public void workWithFaculty() {//=====================================================================================================================================
        System.out.println("======================РОБОТА З ФАКУЛЬТЕТОМ======================");
        System.out.println("1. показати факультети\n2. створити факультет\n" +
                "3. видалити факультет\n4. змінити інформацію про факультет\n0. вийти\n");
        int choice1;
        while (true) {
            System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
            String input = scanner.nextLine();
            try {
                choice1 = Integer.parseInt(input);
                if (choice1 >= 0 && choice1 <= 4)
                    break;
                else
                    System.out.println("ПОМИЛКА. Введіть число зі списку : ");

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (choice1 == 0) {
            System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
            introduction();
        }
        switch (choice1) {
            case 1 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                System.out.println("\n--------ВИВІД СПИСКУ ФАКУЛЬТЕТІВ--------");
                List<Faculty> list = service.getAllFaculties();
                if (list.isEmpty()) {
                    System.out.println("Жодного факультету поки не зареєстровано :(");
                } else {
                    list.forEach(System.out::println);
                }

                System.out.println("\n--------ФАКУЛЬТЕТИ--------\n------------------------");
            }
            case 2 -> {/// ///???????щодо декану/////
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }

                System.out.println("\n--------СТВОРЮЄМО ФАКУЛЬТЕТ--------");
                System.out.println("Введіть код факультету : ");
                String code = textIsNotNull();

                System.out.println("Введіть повну назву факультету : ");
                String name = textIsNotNull();

                System.out.println("Введіть коротку назву (абревіатуру) : ");
                String shortName = textIsNotNull();

                System.out.println("Введіть контакти (email/phone) : ");
                String contacts = textIsNotNull();

                Teacher dean = null;
                //boolean laterAdd=false;

                            /*while (dean == null) {
                                System.out.println("Введіть ПІБ викладача, який буде деканом: ");

                                System.out.println("Введіть ім'я викладача : ");
                                String firstName = textIsNotNull();

                                System.out.println("Введіть прізвище викладача : ");
                                String lastName = textIsNotNull();

                                System.out.println("Введіть по батькові викладача : ");
                                String middleName = textIsNotNull();

                                dean = service.deanFindByPIB(firstName, lastName, middleName);
                                if(dean == null){
                                    System.out.println("Викладача з таким прізвищем не знайдено! Спробуйте ще раз.");

                                    while (true) {
                                        System.out.println("Бажаєте вийти у головене меню? 2-продовжити створення, 1-так, 0-ні");
                                        String input = scanner.nextLine();
                                        try {
                                            choice = Integer.parseInt(input);
                                            if (choice <= 2||choice >= 0)
                                                break;
                                            else
                                                System.out.println("Введіть коректне значення");

                                        } catch (NumberFormatException e) {
                                            System.out.println("Введіть коректне значення");
                                        }
                                    }
                                    if(choice == 1)
                                        break;
                                    if(choice == 2){
                                        laterAdd=true;
                                        break;
                                    }

                                }
                            }
                            if(dean == null && laterAdd==true)
                                System.out.println("Помилка створення факультету. Спершу створіть декана");
                            else {*/
                service.addFaculty(code, name, shortName, dean, contacts);
                System.out.println("ФАКУЛЬТЕТ БУЛО ДОДАНО");

            }
            case 3 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                System.out.println("\n--------ВИДАЛЕННЯ ФАКУЛЬТЕТУ--------");
                System.out.println("Введіть назву факультету для видалення : ");
                String facultyName = scanner.nextLine();

                if (service.deleteFaculty(facultyName)) {
                    System.out.println("Факультет видалено");
                } else {
                    System.out.println("Факультет з такою назвою не знайдено");
                }

            }
            case 4 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                System.out.println("\n--------ЗМІНА ДАНИХ ПРО ФАКУЛЬТЕТ--------");
                System.out.println("Введіть стару повну назву факультету : ");
                String name = textIsNotNull();

                System.out.println("Введіть новий код факультету : ");
                String newCode = textIsNotNull();

                System.out.println("Введіть нову повну назву факультету : ");
                String newName = textIsNotNull();

                System.out.println("Введіть нову коротку назву (абревіатуру) : ");
                String newShortName = textIsNotNull();

                System.out.println("Введіть нові контакти (email/phone) : ");
                String newContacts = textIsNotNull();

                Teacher newDean = null;
                //boolean laterAdd=false;

                while (newDean == null) {
                    System.out.println("Введіть ПІБ викладача, який буде деканом");

                    System.out.println("Введіть ім'я викладача : ");
                    String firstName = textIsNotNull();

                    System.out.println("Введіть прізвище викладача : ");
                    String lastName = textIsNotNull();

                    System.out.println("Введіть по батькові викладача : ");
                    String middleName = textIsNotNull();

                    newDean = inmemoryTeachers.deanFindByPIB(firstName, lastName, middleName);
                    if (newDean == null) {
                        System.out.println("Помилка. Такого викладача не знайдено");}
                    else {
                        boolean success = service.updateFaculty(name, newCode, newName, newShortName, newDean, newContacts);
                        if(success){
                            System.out.println("ФАКУЛЬТЕТ БУЛО ЗМІНЕНО");}
                        else {System.out.println("Помилка");
                            break;}
                    }}



                                /*    while (true) {
                                        System.out.println("Бажаєте вийти у головене меню? 2-продовжити створення, 1-так, 0-ні");
                                        String input = scanner.nextLine();
                                        try {
                                            choice = Integer.parseInt(input);
                                            if (choice <= 2||choice >= 0)
                                                break;
                                            else
                                                System.out.println("Введіть коректне значення");

                                        } catch (NumberFormatException e) {
                                            System.out.println("Введіть коректне значення");
                                        }
                                    }
                                    if(choice == 1)
                                        break;
                                    if(choice == 2){
                                        laterAdd=true;
                                        break;
                                    }

                                }

                                 */




            }
        }
    }

    public void workWithDepartment(){//================================================================================================================================
        System.out.println("======================РОБОТА З КАФЕДРОЮ======================");
        System.out.println("1. показати кафедру\n2. створити кафедру\n"+
                "3. видалити кафедру\n4. змінити інформацію про кафедру\n5. додати викладача\n6. додати студента\n7. видалити викладача"+
                "\n8. видалити студента\n0. вийти\n");
        int choice1;
        while (true) {
            System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
            String input = scanner.nextLine();
            try {
                choice1 = Integer.parseInt(input);
                if (choice1 >= 0 && choice1 <= 8)
                    break;
                else
                    System.out.println("ПОМИЛКА. Введіть число зі списку : ");

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (choice1 == 0) {
            System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
              introduction();
        }
        switch (choice1){
            case 1 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }

                System.out.println("\n--------ВИВІД СПИСКУ КАФЕДР--------");
                if (service.getAllFaculties().isEmpty()) {
                    System.out.println("Жодної кафедри поки не зареєстровано :(");
                } else {
                    System.out.println("\n--------КАФЕДРИ--------\n------------------------");
                    service.getAllDepartments().forEach(System.out::println);

                }
            }
            case 2 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }

                System.out.println("\n--------ДОДАВАННЯ КАФЕДРИ--------");

                System.out.println("Введіть код кафедри : ");
                String code = textIsNotNull();

                System.out.println("Введіть повну назву кафедри : ");
                String name = textIsNotNull();

                System.out.println("Введіть локацію : ");
                String location = textIsNotNull();

                System.out.println("Введіть назву факультету, до якого належить кафедра: ");
                String facultyName = scanner.nextLine();

                Teacher head = null;
                            /*
                            while (head == null) {
                                System.out.println("Введіть ім'я викладача, який буде завідувачем кафедри : ");
                                String firstName = textIsNotNull();

                                System.out.println("Введіть прізвище викладача, який буде завідувачем кафедри : ");
                                String lastName = textIsNotNull();

                                System.out.println("Введіть по батькові викладача, який буде завідувачем кафедри : ");
                                String middleName = textIsNotNull();

                                head =  service.headFindByPIB(firstName, lastName, middleName);

                                if (head == null) {
                                    System.out.println("Помилка створення кафедри. Спершу створіть завідувача кафедри");
                                }
                                else {*/
                boolean success = service.addDepartment(facultyName, code, name, head, location);
                if(success)
                    System.out.println("КАФЕДРУ БУЛО ДОДАНО");
                else System.out.println("Факультет не було знайдено");

                //}
                // }
            }
            case 3 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                System.out.println("\n--------ВИДАЛЕННЯ КАФЕДРИ--------");

                System.out.println("ВИДАЛЕННЯ КАФЕДРИ");
                System.out.println("Введіть назву факультету, до якого належить кафедра: ");
                String facultyName = scanner.nextLine();

                System.out.println("Введіть назву кафедри : ");
                String deptName = textIsNotNull();


                if (service.deleteDepartment(facultyName, deptName)){
                    System.out.println("Кафедру видалено");}
                else{
                    System.out.println("Кафедру з такою назвою або факультетом не знайдено ");}

            }
            case 4 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }

                System.out.println("\n--------ОНОВЛЕННЯ КАФЕДРИ--------");

                System.out.println("Введіть назву факультету, до якого належить кафедра: ");
                String facultyName = scanner.nextLine();
                System.out.println("Введіть стару повну назву кафедри : ");
                String name = textIsNotNull();

                System.out.println("Введіть новий код кафедри : ");
                String newCode = textIsNotNull();

                System.out.println("Введіть нову повну назву кафедри : ");
                String newName = textIsNotNull();

                System.out.println("Введіть нову локацію : ");
                String newLocation = textIsNotNull();

                //System.out.println("Введіть назву факультету, до якого належить кафедра: ");
                //String newFacultyName = scanner.nextLine();

                Teacher newHead = null;

                            while (newHead == null) {
                                System.out.println("Введіть ім'я викладача, який буде завідувачем кафедри : ");
                                String firstName = textIsNotNull();

                                System.out.println("Введіть прізвище викладача, який буде завідувачем кафедри : ");
                                String lastName = textIsNotNull();

                                System.out.println("Введіть по батькові викладача, який буде завідувачем кафедри : ");
                                String middleName = textIsNotNull();

                                newHead =  inmemoryTeachers.headFindByPIB(firstName, lastName, middleName);

                                if (newHead == null) {
                                    System.out.println("Помилка. Такого викладача не знайдено");
                                }
                                else {
                boolean success = service.updateDepartment(facultyName, name, newCode, newName, newHead, newLocation);
                if(success){
                    System.out.println("КАФЕДРУ БУЛО ЗМІНЕНО");}
                else {System.out.println("Помилка");
                break;}
            }}}
            case 5 -> {
                System.out.println("\n--------ДОДАЄМО ВИКЛАДАЧА--------");
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
// треба додати можливіть вийти з циклу якщо нема факультета/кафедри
                Faculty faculty = null;
                while (faculty == null) {
                    System.out.print("Введіть назву факультету: ");
                    String facultyInput = scanner.nextLine();
                    faculty = currentUni.findFacultyByName(facultyInput);
                    if (faculty == null) System.out.println("Факультет не знайдено");
                }

                Department department = null;
                while (department == null) {
                    System.out.print("Введіть назву кафедри: ");
                    String deptInput = scanner.nextLine();
                    department = faculty.departmentFindByName(deptInput).orElse(null);
                    if (department == null) System.out.println("Кафедру не знайдено на цьому факультеті!");
                }


                //Faculty faculty = null;

                //while (faculty == null) {
                //    System.out.println("Введіть назву факультету : ");
                //    String input = scanner.nextLine();

                //    try {
                //        faculty = service.findFacultyByName(input);
                //    } catch (IllegalArgumentException e) {
                //        System.out.println("Факультет не знайдено. Введіть коректне значення ");
                //    }
                //}
                //Department department = null;
                // while ( department== null) {
                //    System.out.println("Введіть назву кафедри : ");
                //   String input = scanner.nextLine();
//
                //  try {
                //      department = faculty.findDepartmentByName(input);
                //  } catch (IllegalArgumentException e) {
                //     System.out.println("Кафедру не знайдено. Введіть коректне значення ");
                // }
                // }

                //System.out.println("Введіть факультет : ");
                //String faculty = textIsNotNull();

                //System.out.println("Введіть кафедру : ");
                //String department = textIsNotNull();

                System.out.println("Введіть ID особи : ");
                String id = textIsNotNull();

                System.out.println("Введіть ім'я викладача : ");
                String name = textIsNotNull();

                System.out.println("Введіть прізвище викладача : ");
                String lastName = textIsNotNull();

                System.out.println("Введіть по батькові викладача : ");
                String middleName = textIsNotNull();

                LocalDate birthDate = null;
                while (birthDate == null) {
                    System.out.print("Введіть дату народження викладача (РРРР-ММ-ДД) : ");
                    String input = scanner.nextLine();
                    try {
                        birthDate = LocalDate.parse(input);
                        if (LocalDate.now().getYear() < birthDate.getYear())
                            birthDate = null;
                    } catch (DateTimeParseException e) {
                        System.out.println("Введіть коректне значення ");
                    }
                }

                System.out.println("Введіть емейл викладача : ");
                String email = textIsNotNull();

                System.out.println("Введіть номер телефону викладача : ");
                String phone = textIsNotNull();

                Teacher.TeachersPosition position = null;
                while (position == null) {
                    System.out.println("Введіть посаду викладача (Професор/Доцент/Старший викладач/" +
                            "викладач/асистент/викладач-стажист) : ");
                    String input = scanner.nextLine();

                    try {
                        position = Teacher.TeachersPosition.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                Teacher.TeachersDegree degree = null;
                while (degree == null) {
                    System.out.println("Введіть науковий ступінь (Доктор філософії/Доктор наук/не має) : ");
                    String input = scanner.nextLine();

                    try {
                        degree = Teacher.TeachersDegree.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                Teacher.TeachersAcademicTitle academicTitle = null;
                while (academicTitle == null) {
                    System.out.println("Введіть вчене звання (Доцент/Профеор/Старший дослідник/не має) : ");
                    String input = scanner.nextLine();

                    try {
                        academicTitle = Teacher.TeachersAcademicTitle.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                LocalDate hireData = null;
                while (hireData == null) {
                    System.out.print("Введіть дату працевлаштування викладача тут (РРРР-ММ-ДД) : ");
                    String input = scanner.nextLine();
                    try {
                        hireData = LocalDate.parse(input);
                        if (LocalDate.now().getYear() < hireData.getYear())
                            hireData = null;
                    } catch (DateTimeParseException e) {
                        System.out.println("Введіть коректне значення ");
                    }
                }

                double workload;
                while (true) {
                    try {
                        System.out.println("Введіть кількість годин навантаження на рік: ");
                        workload = Double.parseDouble(scanner.nextLine());

                        if (workload >= 1) break;
                        else System.out.println("Введіть кількість годин навантаження (>=1)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }



                Teacher teacher = new Teacher(id, name, lastName, middleName, birthDate,
                        email, phone, position, degree, academicTitle, hireData, workload, faculty, department);
                if(service.addTeacher(faculty.getName(), department.getName(), teacher))
                    System.out.println("ВИКЛАДАЧА БУЛО ДОДАНО");
                else System.out.println("Не знайдено факультет або кафедру");

            }
            case 6 -> {
                System.out.println("\n--------ДОДАЄМО СТУДЕНТА--------");
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }

                Faculty faculty = null;
                while (faculty == null) {
                    System.out.print("Введіть назву факультету: ");
                    String facultyInput = scanner.nextLine();
                    faculty = currentUni.findFacultyByName(facultyInput);
                    if (faculty == null) System.out.println("Факультет не знайдено");
                }

                Department department = null;
                while (department == null) {
                    System.out.print("Введіть назву кафедри: ");
                    String deptInput = scanner.nextLine();
                    department = faculty.departmentFindByName(deptInput).orElse(null);
                    if (department == null) System.out.println("Кафедру не знайдено на цьому факультеті!");
                }

                System.out.println("Введіть ID студента : ");
                String id = textIsNotNull();

                System.out.println("Введіть ім'я студента : ");
                String name = textIsNotNull();

                System.out.println("Введіть прізвище студента : ");
                String lastName = textIsNotNull();

                System.out.println("Введіть по батькові студента : ");
                String middleName = textIsNotNull();

                LocalDate birthDate = null;
                while (birthDate == null) {
                    System.out.print("Введіть дату народження студента (РРРР-ММ-ДД) : ");
                    String input = scanner.nextLine();
                    try {
                        birthDate = LocalDate.parse(input);
                        if (LocalDate.now().getYear() < birthDate.getYear())
                            birthDate = null;
                    } catch (DateTimeParseException e) {
                        System.out.println("Введіть коректне значення ");
                    }
                }

                System.out.println("Введіть емейл студента : ");
                String email = textIsNotNull();

                System.out.println("Введіть номер телефону студента : ");
                String phone = textIsNotNull();

                System.out.println("Введіть айді картки студента : ");
                String studentCardId = textIsNotNull();

                int course;
                while (true) {
                    try {
                        System.out.println("Введіть курс студента: ");
                        course = Integer.parseInt(scanner.nextLine());

                        if (course >= 1 && course <= 4) break;
                        else System.out.println("Введіть коректний курс (1-4)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число ");
                    }
                }

                int group;
                while (true) {
                    try {
                        System.out.println("Введіть групу студента : ");
                        group = Integer.parseInt(scanner.nextLine());

                        if (group >= 1 && group <= 6) break;
                        else System.out.println("Введіть коректну групу (1-6)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }

                int admissionYear;
                while (true) {
                    try {
                        System.out.println("Введіть рік вступу студента : ");
                        admissionYear = Integer.parseInt(scanner.nextLine());

                        if (LocalDate.now().getYear() > admissionYear) break;
                        else System.out.println("Введіть коректний рік вступу");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }


                Student.TuitionForm tuitionForm = null;
                while (tuitionForm == null) {
                    System.out.println("Введіть форму навчання (Бюджет/Контракт) : ");
                    String input = scanner.nextLine();

                    try {
                        tuitionForm = Student.TuitionForm.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                Student.StudentStatus studentStatus = null;
                while (studentStatus == null) {
                    System.out.println("Введіть статус навчання (Навчається/В академ відпустці/Відрахований) : ");
                    String input = scanner.nextLine();

                    try {
                        studentStatus = Student.StudentStatus.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення ");
                    }
                }


                //service.addStudent(id, name, lastName, middleName, birthDate, email, phone,
                ///        studentCardId, course, group, admissionYear, tuitionForm, studentStatus, faculty,department);

                Student student = new Student(id, name, lastName, middleName, birthDate, email, phone,
                        studentCardId, course, group, admissionYear, tuitionForm, studentStatus, faculty, department);
                if(service.addStudent(faculty.getName(), department.getName(), student))
                    System.out.println("СТУДЕНТА БУЛО ДОДАНО");
                else System.out.println("Не знайдено факультет або кафедру");

            }
            case 7 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }

                System.out.println("\n--------ВИДАЛЕННЯ ВИКЛАДАЧА--------");
                System.out.println("Введіть ID викладача : ");
                String id = textIsNotNull();

                if (service.deleteTeacher(id))
                    System.out.println("Викладача видалено");
                else System.out.println("Викладача не знайдено");

            }
            case 8 -> {

                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                System.out.println("\n--------ВИДАЛЕННЯ СТУДЕНТА--------");
                System.out.println("Введіть ID студента : ");
                String id = textIsNotNull();

                if (service.deleteStudent(id))
                    System.out.println("Студента видалено");
                else System.out.println("Помилка: Студента не знайдено");

            }
        }
    }

    public void workWithTeachers(){//===================================================================================================================================
        System.out.println("======================РОБОТА З ВИКЛАДАЧАМИ======================");
        System.out.println("1. показати викладачів\n2. змінити інформацію про викладача\n" +
                "3. пошук викладача за піб\n0. вийти\n");
        int choice1;
        while (true) {
            System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
            String input = scanner.nextLine();
            try {
                choice1 = Integer.parseInt(input);
                if (choice1 >= 0 && choice1 <= 3)
                    break;
                else
                    System.out.println("ПОМИЛКА. Введіть число зі списку : ");

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (choice1 == 0) {
            System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
               introduction();
        }
        switch (choice1){
            case 1 -> {

                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ВИВІД СПИСКУ ВИКЛАДАЧІВ--------");
                List<Teacher> teachers = inmemoryTeachers.getAllTeachers();
                if (teachers.isEmpty())
                    System.out.println("Жодного викладача поки не зареєстровано :(");
                else {
                    System.out.println("\n--------ВИКЛАДАЧІ--------\n------------------------");
                    teachers.forEach(System.out::println);
                }

                //    if (service.getAllTeachers().isEmpty()) {
                //        System.out.println("Жодного викладача поки не зареєстровано :(");
                ///  } else {
                //   System.out.println("\n--------ВИКЛАДАЧІ--------\n------------------------");
                // service.getAllTeachers().forEach(System.out::println);
                //     }
            }

            case 2 -> {

                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ЗМІНЮЄМО ДАНІ ВИКЛАДАЧА--------");
                System.out.println("Введіть ID викладача, дані якого хочете змінити : ");
                String id = textIsNotNull();

                System.out.println("Введіть нове ім'я викладача : ");
                String name = textIsNotNull();

                System.out.println("Введіть нове прізвище викладача : ");
                String lastName = textIsNotNull();

                System.out.println("Введіть нове по батькові викладача : ");
                String middleName = textIsNotNull();


                System.out.println("Введіть новий емейл викладача : ");
                String email = textIsNotNull();

                System.out.println("Введіть новий номер телефону викладача : ");
                String phone = textIsNotNull();

                Teacher.TeachersPosition position = null;
                while (position == null) {
                    System.out.println("Введіть нову посаду викладача (Професор/доцент/старший викладач/" +
                            "викладач/асистент/викладач-стажист) : ");
                    String input = scanner.nextLine();

                    try {
                        position = Teacher.TeachersPosition.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                Teacher.TeachersDegree degree = null;
                while (degree == null) {
                    System.out.println("Введіть новий науковий ступінь (Доктор філососфії/Доктор наук/не має) : ");
                    String input = scanner.nextLine();

                    try {
                        degree = Teacher.TeachersDegree.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                Teacher.TeachersAcademicTitle academicTitle = null;
                while (academicTitle == null) {
                    System.out.println("Введіть нове вчене звання (Доцент/Професор/Старший дослідник) : ");
                    String input = scanner.nextLine();

                    try {
                        academicTitle = Teacher.TeachersAcademicTitle.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть коректне значення");
                    }
                }

                double workload;
                while (true) {
                    try {
                        System.out.println("Введіть нову кількість годин навантаження на рік: ");
                        workload = Double.parseDouble(scanner.nextLine());

                        if (workload >= 1) break;
                        else System.out.println("Введіть нову кількість годин навантаження (>=1)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }



                if(inmemoryTeachers.updateTeacher(id, name, lastName, middleName,
                      email, phone, position, degree, academicTitle, workload)){
                   System.out.println("ДАНІ ВИКЛАДАЧА БУЛО ЗМІНЕНО");
               }else {
                   System.out.println("Викладача не знайдено");
               }


            }
            case 3 -> {

                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ПОШУК ВИКЛАДАЧА ЗА ПІБ--------");
                System.out.println("Введіть ім'я викладача : ");
                String firstName = textIsNotNull();

                System.out.println("Введіть прізвище викладача : ");
                String lastName = textIsNotNull();

                System.out.println("Введіть по батькові викладача : ");
                String middleName = textIsNotNull();

                inmemoryTeachers.searchingTeacherByPIB(firstName, lastName, middleName);
            }

        }
    }

    public  void workWithStudents(){//==================================================================================================================================
        System.out.println("======================РОБОТА ЗІ СТУДЕНТАМИ======================");
        System.out.println("1. показати студентів\n2. змінити інформацію про" +
                " студента\n3. пошук студента за ПІБ\n4. пошук студента за курсом\n5. пошук студента за групою\n" +
                "6. відсортувати студентів за курсом\n0. вийти\n");
        int choice1;
        while (true) {
            System.out.print("==== ОБЕРІТЬ ПУНКТ ====");
            String input = scanner.nextLine();
            try {
                choice1 = Integer.parseInt(input);
                if (choice1 >= 0 && choice1 <= 6)
                    break;
                else
                    System.out.println("ПОМИЛКА. Введіть число зі списку : ");

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (choice1 == 0) {
            System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
               introduction();
        }
        switch (choice1){
            case 1 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ВИВІД СПИСКУ СТУДЕНТІВ--------");
                List<Student> students = inmemoryStudents.getAllStudents();
                if (students.isEmpty())
                    System.out.println("Жодного студента поки не зареєстровано :(");
                else {
                    System.out.println("\n--------СТУДЕНТИ--------\n------------------------");
                    students.forEach(System.out::println);
                }

                //if (service.getAllStudents().isEmpty()) {
                //    System.out.println("Жодного студенту поки не зареєстровано :(");
                //} else {
                //    System.out.println("\n--------СТУДЕНТИ--------\n------------------------");
                //    service.getAllStudents().forEach(System.out::println);
                //}
            }

            case 2 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ЗМІНЮЄМО ДАНІ СТУДЕНТА--------");
                System.out.println("Введіть ID студента, дані якого хочете змінити : ");
                String id = textIsNotNull();

                System.out.println("Введіть нове ім'я студента : ");
                String newName = textIsNotNull();

                System.out.println("Введіть нове прізвище студента : ");
                String newLastName = textIsNotNull();

                System.out.println("Введіть нове по батькові студента : ");
                String newMiddleName = textIsNotNull();

                System.out.println("Введіть новий емейл студента : ");
                String newEmail = textIsNotNull();

                System.out.println("Введіть новий номер телефону студента : ");
                String newPhone = textIsNotNull();

                int newCourse;
                while (true) {
                    try {
                        System.out.println("Введіть новий курс студента : ");
                        newCourse = Integer.parseInt(scanner.nextLine());

                        if (newCourse >= 1 && newCourse <= 4) break;
                        else System.out.println("Введіть коректний курс (1-4)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }


                int newGroup;
                while (true) {
                    try {
                        System.out.println("Введіть нову групу студента : ");
                        newGroup = Integer.parseInt(scanner.nextLine());

                        if (newGroup >= 1 && newGroup <= 6) break;
                        else System.out.println("Введіть коректну групу (1-6)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }

                Student.TuitionForm newTuitionForm = null;
                while (newTuitionForm == null) {
                    System.out.println("Введіть оновлену форму навчання (Бюджет/Контракт) : ");
                    String input = scanner.nextLine();

                    try {
                        newTuitionForm = Student.TuitionForm.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть форму з поданих");
                    }
                }

                Student.StudentStatus newStatus = null;
                while (newStatus == null) {
                    System.out.println("Введіть оновлений статус навчання (Навчається/В академ відпустці/Відрахований) : ");
                    String input = scanner.nextLine();

                    try {
                        newStatus = Student.StudentStatus.fromString(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Введіть статус з поданих");
                    }
                }


                if (inmemoryStudents.updateStudent(id, newName, newLastName, newMiddleName, newEmail, newPhone,
                        newCourse, newGroup, newTuitionForm, newStatus)) {
                   System.out.println("ДАНІ СТУДЕНТА ЗМІНЕНО.");
                } else {
                   System.out.println("Такого студента не знайдено =(");
                }

            }
            case 3 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ПОШУК СТУДЕНТА ЗА ПІБ--------");
                System.out.println("Введіть ім'я студента : ");
                String firstName = textIsNotNull();

                System.out.println("Введіть прізвище студента : ");
                String lastName = textIsNotNull();

                System.out.println("Введіть по батькові студента : ");
                String middleName = textIsNotNull();

                inmemoryStudents.searchingByPIB(firstName, lastName, middleName);
            }
            case 4 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ПОШУК СТУДЕНТА ЗА КУРСОМ--------");
                int course;
                while (true) {
                    try {
                        System.out.println("Введіть курс: ");
                        course = Integer.parseInt(scanner.nextLine());

                        if (course >= 1 && course <= 4) {
                             inmemoryStudents.searchingByCourse(course);
                            break;
                        } else System.out.println("Введіть коректний курс (1-4)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }
            }
            case 5 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }
                System.out.println("\n--------ПОШУК СТУДЕНТА ЗА ГРУПОЮ--------");
                int group;
                while (true) {
                    try {
                        System.out.println("Введіть групу: ");
                        group = Integer.parseInt(scanner.nextLine());

                        if (group >= 1 && group <= 6) {
                            inmemoryStudents.searchingByGroup(group);
                            break;
                        } else System.out.println("Введіть коректну групу (1-6)");
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть число");
                    }
                }
            }
            case 6 -> {
                University currentUni = service.getUniversity();
                if (currentUni == null) {
                    System.out.println("Спочатку створіть університет ");
                    break;
                }
                if (currentUni.facultiesExists() == false){
                    System.out.println("Спочатку створіть факультет");
                    break;
                }
                Faculty myFaculty = currentUni.getFaculties().get(0);
                if (myFaculty.departmentExists() == false){
                    System.out.println("Спочатку створіть кафедру");
                    break;
                }

                System.out.println("\n--------СОРТУВАННЯ СТУДЕНТІВ ЗА ГРУПАМИ--------");
                inmemoryStudents.sortingCourse();
            }
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
        System.out.println("0. вийти з програми");
    }



}
