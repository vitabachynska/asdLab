package packageFiles;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceCRUD service = new ServiceCRUD();

        while (true) {
            System.out.println("Вас вітає онлайн-сервіс 'My University'");
            System.out.println("Тут ви можете шукати, переглядати та змінювати" +
                    " дані про студентів та вчителів, ");
            System.out.println("А також додавати нових людей у систему");
            System.out.println("------МЕНЮ------");
            System.out.println("----------------");
            System.out.println("натисність відповідну клавішу");
            System.out.println("----------------");
            System.out.println("1 - Додати студента");
            System.out.println("2 - Показати студентів");
            System.out.println("3 - Змінити дані");
            System.out.println("4 - Видалити студента");
            System.out.println("5 - Пошук студента за іменем. прізвищем та по батькові");
            System.out.println("6 - Пошук студента за курсом");
            System.out.println("7 - Пошук студента за групою");
            System.out.println("0 - Вийти з меню");

            int choice;


/// ///////????????/////// перевірка на ввід
            while (true) {
                System.out.print("Оберіть пункт меню: ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 0 && choice <= 7)
                        break;
                    else
                        System.out.println("Ви ввели не число зі списку. Спробуйте ще раз : ");

                } catch (NumberFormatException e) {
                    System.out.println("Введіть число");
                }
            }
            if (choice == 0) {
                System.out.println("Вихід з програми");
                break;
            }

            switch (choice) {
                case 1 -> {
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
                        System.out.print("Введіть дату народження студента (РРРР-ММ-ДД): ");
                        String input = scanner.nextLine();
                        try {
                            birthDate = LocalDate.parse(input);
                            if (LocalDate.now().getYear() < birthDate.getYear())
                                birthDate = null;
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println("Введіть коректне значення");
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
                            System.out.println("Введіть число");
                        }
                    }

                    int group;
                    while (true) {
                        try {
                            System.out.println("Введіть групу студента: ");
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
                            System.out.println("Введіть рік вступу студента: ");
                            admissionYear = Integer.parseInt(scanner.nextLine());

                            if (LocalDate.now().getYear() > admissionYear) break;
                            else System.out.println("Введіть коректний рік вступу");
                        } catch (NumberFormatException e) {
                            System.out.println("Введіть число");
                        }
                    }


                    Student.TuitionForm tuitionForm = null;
                    while (tuitionForm == null) {
                        System.out.println("Введіть форму навчання (Бюджет/Контракт): ");
                        String input = scanner.nextLine();

                        try {
                            tuitionForm = Student.TuitionForm.fromString(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }

                    Student.StudentStatus studentStatus = null;
                    while (studentStatus == null) {
                        System.out.println("Введіть статус навчання (Навчається/В академ відпустці/Відрахований): ");
                        String input = scanner.nextLine();

                        try {
                            studentStatus = Student.StudentStatus.fromString(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Введіть коректне значення");
                        }
                    }

                    service.addStudent(id, name, lastName, middleName, birthDate, email, phone,
                            studentCardId, course, group, admissionYear, tuitionForm, studentStatus);

                    System.out.println("Студента додано");
                }

                case 2 -> {
                    if (service.getAll().isEmpty()) {
                        System.out.println("Жодного студенту не зареєстровано");
                    } else {
                        service.getAll().forEach(System.out::println);
                    }
                }

                case 3 -> {
                    System.out.println("Введіть ID студента, дані якого хочете змінити :");
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
                            System.out.println("Введіть новий курс студента: ");
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
                            System.out.println("Введіть нову групу студента: ");
                            newGroup = Integer.parseInt(scanner.nextLine());

                            if (newGroup >= 1 && newGroup <= 6) break;
                            else System.out.println("Введіть коректну групу (1-6)");
                        } catch (NumberFormatException e) {
                            System.out.println("Введіть число");
                        }
                    }

                    Student.TuitionForm newTuitionForm = null;
                    while (newTuitionForm == null) {
                        System.out.println("Введіть оновлену форму навчання (Бюджет/Контракт): ");
                        String input = scanner.nextLine();

                        try {
                            newTuitionForm = Student.TuitionForm.fromString(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Введіть кокектне значення");
                        }
                    }

                    Student.StudentStatus newStatus = null;
                    while (newStatus == null) {
                        System.out.println("Введіть оновлений статус навчання (Навчається/В академ відпустці/Відрахований): ");
                        String input = scanner.nextLine();

                        try {
                            newStatus = Student.StudentStatus.fromString(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Введіть кокектне значення");
                        }
                    }

                    if (service.updateStudent(id, newName, newLastName, newMiddleName, newEmail, newPhone,
                            newCourse, newGroup, newTuitionForm, newStatus)) {
                        System.out.println("Дані студента змінено.");
                    } else {
                        System.out.println("Такого студента не знайдено.");
                    }

                }

                case 4 -> {
                    System.out.println("Введіть ID студента : ");
                    String id = textIsNotNull();

                    if (service.deleteStudent(id)) {
                        System.out.println("Студента видалено.");
                    } else {
                        System.out.println("Студента не знайдено.");
                    }

                }

                case 5 -> {
                    System.out.println("Введіть ім'я студента : ");
                    String firstName = textIsNotNull();

                    System.out.println("Введіть прізвище студента : ");
                    String lastName = textIsNotNull();

                    System.out.println("Введіть по батькові студента : ");
                    String middleName = textIsNotNull();

                    service.searchingByPIB(firstName, lastName, middleName);
                }

                case 6 -> {
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

                case 7 -> {
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
            }
        }
    }
    private static String textIsNotNull(){
        String text;
        Scanner scanner = new Scanner(System.in);
        while (true){
            text = scanner.nextLine();
            if(text == null || text.isEmpty())
                System.out.println("Введіть значення");
            else break;
        }
        return text;
    }
}


