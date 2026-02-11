package packageFiles;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceCRUD service = new ServiceCRUD();

        while (true) {
            System.out.println("Вас вітає онлайн проєкт 'DIGIUNI'!");
            System.out.println("Тут ви можете шукати, переглядати та змінювати" +
                    " дані про студентів та викладачів.\nНижче представлене меню, де " +
                    "можна здійснювати подані операції. ");
            System.out.println("------МЕНЮ------");
            System.out.println("----------------");
            System.out.println("натисність відповідну клавішу");
            System.out.println("----------------");
            System.out.println("1 - Додати університет");
            System.out.println("2 - Додати факультет");
            System.out.println("3 - Показати університет/факультет");
            System.out.println("4 - Додати студента/викладача");
            System.out.println("5 - Показати студентів/викладачів");
            System.out.println("6 - Змінити дані");
            System.out.println("7 - Видалити студента/викладача");
            System.out.println("8 - Пошук студента/викладача за іменем, прізвищем та по батькові");
            System.out.println("9 - Пошук студента за курсом");
            System.out.println("10 - Пошук студента за групою");
            System.out.println("11 - Сортування студентів за групами");
            System.out.println("0 - Вийти з програми");

            int choice;


            while (true) {
                System.out.print("ОБЕРІТЬ ПУНКТ МЕНЮ: ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 0 && choice <= 11)
                        break;
                    else
                        System.out.println("Ви ввели число не зі списку. Спробуйте ще раз : ");

                } catch (NumberFormatException e) {
                    System.out.println("Введіть коректне значення ");
                }
            }
            if (choice == 0) {
                System.out.println("--ВИХІД З ПРОГРАМИ--");
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--ДОДАВАННЯ УНІВЕРСИТЕТУ--");
                    System.out.println("Введіть назву університету : ");
                    String fullName = textIsNotNull();

                    System.out.println("Введіть скорочену назву університету : ");
                    String shortName = textIsNotNull();

                    System.out.println("Введіть місто знаходження університету : ");
                    String city = textIsNotNull();

                    System.out.println("Введіть адресу університету : ");
                    String address = textIsNotNull();

                    service.addUniversity(fullName, shortName, city, address);
                    System.out.println("УНІВЕРСИТЕТ БУЛО ДОДАНО");

                }
                case 2 -> {
                    System.out.println("\n--ДОДАВАННЯ ФАКУЛЬТЕТУ--");
                    System.out.println("Введіть код факультету : ");
                    String code = textIsNotNull();

                    System.out.println("Введіть повну назву факультету : ");
                    String name = textIsNotNull();

                    System.out.println("Введіть коротку назву (абревіатуру) : ");
                    String shortName = textIsNotNull();

                    System.out.println("Введіть контакти (email/phone) : ");
                    String contacts = textIsNotNull();

                    Teacher dean = null;


                        while (dean == null) {
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
                                    System.out.println("Бажаєте вийти у головене меню? 1-так, 0-ні");
                                    String input = scanner.nextLine();
                                    try {
                                        choice = Integer.parseInt(input);
                                        if (choice == 1||choice == 0)
                                            break;
                                        else
                                            System.out.println("Введіть коректне значення");

                                    } catch (NumberFormatException e) {
                                        System.out.println("Введіть коректне значення");
                                    }
                                }
                                if(choice == 1)
                                    break;
                            }

                        }
                    if(dean == null)
                        System.out.println("Помилка створення факультету. Спершу створіть декана");
                    else {
                        service.addFaculty(code, name, shortName, dean, contacts);
                        System.out.println("ФАКУЛЬТЕТ БУЛО ДОДАНО");
                    }
                }
                case 3 ->{
                    while (true) {
                        System.out.println("\nПоказати університет чи факультети? 1 - університет, 2 - факультет : ");
                        String input = scanner.nextLine();
                        try {
                            choice = Integer.parseInt(input);
                            if (choice == 1||choice == 2)
                                break;
                            else
                                System.out.println("Введіть коректне значення");

                        } catch (NumberFormatException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }
                    if (choice==1){
                        System.out.println("\n--УНІВЕРСИТЕТ--");
                        if (service.getUniversity() == null) {
                            System.out.println("Університет поки не зареєстровано :(");
                        } else {
                            System.out.println(service.getUniversity().toString());

                        }
                    }
                    if (choice==2){
                        System.out.println("\n--ВИВІД СПИСКУ--");
                        if (service.getAllFaculties().isEmpty()) {
                            System.out.println("Жодного факультету поки не зареєстровано :(");
                        } else {
                            System.out.println("\n--------ФАКУЛЬТЕТИ--------\n------------------------");
                            service.getAllFaculties().forEach(System.out::println);

                        }
                    }
                }

                case 4 -> {
                    while (true) {
                        System.out.println("\nДодаємо студента чи викладача? 1 - студента, 2 - викладача : ");
                        String input = scanner.nextLine();
                        try {
                            choice = Integer.parseInt(input);
                            if (choice == 1||choice == 2)
                                break;
                            else
                                System.out.println("Введіть коректне значення");

                        } catch (NumberFormatException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }


                    if(choice==1){
                        System.out.println("\n--ДОДАВАННЯ СТУДЕНТА--");
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
                            } catch (java.time.format.DateTimeParseException e) {
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

                        service.addStudent(id, name, lastName, middleName, birthDate, email, phone,
                                studentCardId, course, group, admissionYear, tuitionForm, studentStatus);

                        System.out.println("СТУДЕНТА БУЛО ДОДАНО");
                    }
                    if(choice == 2){

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
                            } catch (java.time.format.DateTimeParseException e) {
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
                            } catch (java.time.format.DateTimeParseException e) {
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


                        service.addTeacher(id, name, lastName, middleName, birthDate,
                                email, phone, position, degree, academicTitle, hireData, workload);

                        System.out.println("ВИКЛАДАЧА БУЛО ДОДАНО");
                    }
                }

                case 5 -> {
                    System.out.println("\n--ВИВІД СПИСКУ--");
                    if (service.getAllStudents().isEmpty()) {
                        System.out.println("Жодного студенту поки не зареєстровано :(");
                    } else {
                        System.out.println("\n--------СТУДЕНТИ--------\n------------------------");
                        service.getAllStudents().forEach(System.out::println);

                    }
                    if (service.getAllTeachers().isEmpty()) {
                        System.out.println("Жодного викладача поки не зареєстровано :(");
                    } else {
                        System.out.println("\n--------ВИКЛАДАЧІ--------\n------------------------");
                        service.getAllTeachers().forEach(System.out::println);
                    }


                }

                case 6 -> {
                    System.out.println("\n--ОНОВЛЕННЯ ДАНИХ--");

                    while (true) {
                        System.out.println("\nОновлюємо дані про студента чи викладача? 1 - студента, 2 - викладача : ");
                        String input = scanner.nextLine();
                        try {
                            choice = Integer.parseInt(input);
                            if (choice == 1||choice == 2)
                                break;
                            else
                                System.out.println("Введіть коректне значення");

                        } catch (NumberFormatException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }

                    if(choice == 1){
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

                        if (service.updateStudent(id, newName, newLastName, newMiddleName, newEmail, newPhone,
                                newCourse, newGroup, newTuitionForm, newStatus)) {
                            System.out.println("ДАНІ СТУДЕНТА ЗМІНЕНО.");
                        } else {
                            System.out.println("Такого студента не знайдено =(");
                        }

                    }
                    if(choice == 2){
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
                            System.out.println("Введіть нове вчене звання (Доцент/Профеор/Старший дослідник) : ");
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


                        service.updateTeacher(id, name, lastName, middleName,
                                email, phone, position, degree, academicTitle, workload);

                        System.out.println("ДАНІ ВИКЛАДАЧА БУЛО ЗМІНЕНО");
                    }



                }

                case 7 -> {
                    System.out.println("\n--ВИДАЛЕННЯ--");

                    while (true) {
                        System.out.println("\nВидаляємо студента чи викладача? 1 - студента, 2 - викладача : ");
                        String input = scanner.nextLine();
                        try {
                            choice = Integer.parseInt(input);
                            if (choice == 1||choice == 2)
                                break;
                            else
                                System.out.println("Введіть коректне значення");

                        } catch (NumberFormatException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }
                    if (choice == 1){
                        System.out.println("Введіть ID студента : ");
                        String id = textIsNotNull();

                        if (service.deleteStudent(id)) {
                            System.out.println("Студента видалено.");
                        } else {
                            System.out.println("Студента не знайдено =(");
                        }
                    }
                    if(choice == 2){
                        System.out.println("Введіть ID викладача : ");
                        String id = textIsNotNull();

                        if (service.deleteTeacher(id)) {
                            System.out.println("Викладача видалено.");
                        } else {
                            System.out.println("Викладача не знайдено =(");
                        }
                    }

                }

                case 8 -> {
                    System.out.println("\n--ПОШУК ЗА ПІБ--");

                    while (true) {
                        System.out.println("\nШукаємо студента чи викладача? 1 - студента, 2 - викладача : ");
                        String input = scanner.nextLine();
                        try {
                            choice = Integer.parseInt(input);
                            if (choice == 1||choice == 2)
                                break;
                            else
                                System.out.println("Введіть коректне значення");

                        } catch (NumberFormatException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }
                    if(choice==1) {
                        System.out.println("Введіть ім'я студента : ");
                        String firstName = textIsNotNull();

                        System.out.println("Введіть прізвище студента : ");
                        String lastName = textIsNotNull();

                        System.out.println("Введіть по батькові студента : ");
                        String middleName = textIsNotNull();

                        service.searchingByPIB(firstName, lastName, middleName);
                    }
                    if (choice==2){
                        System.out.println("Введіть ім'я викладача : ");
                        String firstName = textIsNotNull();

                        System.out.println("Введіть прізвище викладача : ");
                        String lastName = textIsNotNull();

                        System.out.println("Введіть по батькові викладача : ");
                        String middleName = textIsNotNull();

                        service.searchingTeacherByPIB(firstName, lastName, middleName);
                    }
                }

                case 9 -> {
                    System.out.println("\n--ПОШУК СТУДЕНТА ЗА КУРСОМ--");
                    int course;
                    while (true) {
                        try {
                            System.out.println("Введіть курс: ");
                            course = Integer.parseInt(scanner.nextLine());

                            if (course >= 1 && course <= 4) {
                                service.searchingByCourse(course);
                                break;
                            } else System.out.println("Введіть коректний курс (1-4)");
                        } catch (NumberFormatException e) {
                            System.out.println("Введіть число");
                        }
                    }
                }

                case 10 -> {
                    System.out.println("\n--ПОШУК СТУДЕНТА ЗА ГРУПОЮ--");
                    int group;
                    while (true) {
                        try {
                            System.out.println("Введіть групу: ");
                            group = Integer.parseInt(scanner.nextLine());

                            if (group >= 1 && group <= 6) {
                                service.searchingByGroup(group);
                                break;
                            } else System.out.println("Введіть коректну групу (1-6)");
                        } catch (NumberFormatException e) {
                            System.out.println("Введіть число");
                        }
                    }
                }
                case 11 -> {
                    System.out.println("\n--СОРТУВАННЯ СТУДЕНТІВ ЗА ГРУПАМИ--");
                    service.sortingCourse();
                }
            }

            while (true) {
                System.out.println("- - - - - - - - - - - -\nБажаєте повернутися до меню? 1 - так, 0 - ні : ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice == 1||choice == 0)
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

