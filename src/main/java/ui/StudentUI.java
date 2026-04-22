package ui;

import domain.*;
import repository.InmemoryStudents;
import service.*;

import java.util.List;

import static service.UtilityValidation.*;

public class StudentUI {
    private Service service;
    private InmemoryStudents studentRepository;

    public StudentUI(Service service) {
        this.service = service;
        this.studentRepository = service.studentRepository;
    }

    public void workWithStudents() {
        while (true) {
            printMenu();
            int choice = readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 6);
            if (choice == 0) break;
            if (!UtilityValidation.isUniversityExist(service)) continue;

            switch (choice) {
                case 1 -> showStudents();
                case 2 -> updateStudent();
                case 3 -> searchStudentByPIB();
                case 4 -> searchStudentByCourse();
                case 5 -> searchStudentByGroup();
                case 6 -> sortStudentsByCourse();
            }
        }
    }
    private void showStudents() {
        System.out.println("\n--------ВИВІД СПИСКУ СТУДЕНТІВ--------");
        if (service.studentRepository.getAllStudents().isEmpty()) {
            System.out.println("Жодного викладача поки не зареєстровано :(");
        } else {
            service.studentRepository.getAllStudents().forEach(System.out::println);
        }
    }


    private void updateStudent() {
        System.out.println("\n--------ОНОВЛЮЄМО ДАНІ СТУДЕНТА--------");
        // if (!checkRights()) return;
        Student oldStudent = service.findStudentInteractively();
        if (oldStudent == null) return;
        String newName = UtilityValidation.askInput("Нове ім'я: ");
        String newLastName = UtilityValidation.askInput("Нове прізвище: ");
        String newMiddleName = UtilityValidation.askInput("Нове по батькові: ");
        String newEmail = UtilityValidation.askInput("Новий email: ");
        String newPhone = UtilityValidation.askInput("Новий телефон: ");
        int newCourse = UtilityValidation.readInt("Новий курс (1-4): ", 1, 4);
        int newGroup = UtilityValidation.readInt("Нова група (1-6): ", 1, 6);
        Student.TuitionForm newForm = UtilityValidation.readEnum(Student.TuitionForm.class,
                "Нова форма навчання (Бюджет/Контракт): ");
        Student.StudentStatus newStatus = UtilityValidation.readEnum(Student.StudentStatus.class,
                "Новий статус (Навчається/В академ відпустці/Відрахований): ");
        if (studentRepository.updateStudent(oldStudent.getId(), newName, newLastName, newMiddleName,
                newEmail, newPhone, newCourse, newGroup, newForm, newStatus)) {
            service.syncWithFile();
            System.out.println("ДАНІ СТУДЕНТА УСПІШНО ОНОВЛЕНО");
        } else {
            System.out.println("Помилка: Не вдалося оновити дані студента.");
        }
    }

    private void searchStudentByPIB() {
        System.out.println("\n--------ПОШУК СТУДЕНТА ЗА ПІБ--------");
        String name = askInput("Ім'я: ");
        String lName = askInput("Прізвище: ");
        String mName = askInput("По батькові: ");

        studentRepository.searchingByPIB(name, lName, mName);
    }

    private void searchStudentByCourse() {
        System.out.println("\n--------ПОШУК СТУДЕНТІВ ЗА КУРСОМ--------");
        int course = readInt("Введіть курс (1-4): ", 1, 4);

        List<Student> found = service.findAny(service.studentRepository.getAllStudents(), s -> s.getCourse() == course);
        if (found.isEmpty()) {
            System.out.println("Студентів на " + course + " курсі не знайдено.");
        } else {
            found.forEach(System.out::println);
        }
    }

    private void searchStudentByGroup() {
        System.out.println("\n--------ПОШУК СТУДЕНТІВ ЗА ГРУПОЮ--------");
        int group = readInt("Введіть номер групи (1-6): ", 1, 6);

        List<Student> found = service.findAny(studentRepository.getAllStudents(), s -> s.getGroup() == group);
        if (found.isEmpty()) {
            System.out.println("Студентів у " + group + " групі не знайдено.");
        } else {
            found.forEach(System.out::println);
        }
    }

    private void sortStudentsByCourse() {
        System.out.println("\n--------СОРТУВАННЯ СТУДЕНТІВ ЗА КУРСОМ--------");
        studentRepository.sortingCourse();
        System.out.println("Студентів відсортовано.");
    }

    private void printMenu() {
        System.out.println("\n====================== РОБОТА ЗІ СТУДЕНТАМИ ======================");
        System.out.println("1. Показати всіх студентів");
        System.out.println("2. Змінити інформацію про студента");
        System.out.println("3. Пошук студента за ПІБ");
        System.out.println("4. Пошук студента за курсом");
        System.out.println("5. Пошук студента за групою");
        System.out.println("6. Відсортувати студентів за курсом");
        System.out.println("0. Вийти в головне меню");
    }
}
