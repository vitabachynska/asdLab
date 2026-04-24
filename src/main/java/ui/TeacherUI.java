package ui;

import domain.*;
import exceptions.*;
import repository.InmemoryTeachers;
import service.Authorization;
import service.RoleForm;
import service.Service;
import service.UtilityValidation;

import static service.UtilityValidation.*;

public class TeacherUI {
    private Service service;
    private InmemoryTeachers teacherRepository;

    public TeacherUI(Service service) {
        this.service = service;
        this.teacherRepository = service.teacherRepository;
    }

    public void workWithTeachers(){
        try {
        while (true) {
            printMenu();
            int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 4);
            if (choice == 0) break;
            if (!UtilityValidation.isUniversityExist(service)) continue;

            switch (choice) {
                case 1 -> showTeachers();
                case 2 -> {if (Authorization.can(RoleForm.EDIT.getMask())) updateTeacher();
                            else System.out.println("У вас немає прав на оновлення викладачів");}
                case 3 -> searchTeacherByPIB();
                case 4 -> alfabetSortFaculty();
            }
        }}catch (UniversityException e){
                System.out.println("Помилка: " + e.getMessage());
            }
        }


    private void showTeachers() {
        System.out.println("\n--------ВИВІД СПИСКУ ВИКЛАДАЧІВ--------");
        if (service.teacherRepository.getAllTeachers().isEmpty()) {
            System.out.println("Жодного викладача поки не зареєстровано :(");
        } else {
            service.teacherRepository.getAllTeachers().forEach(System.out::println);
        }
    }

    private void updateTeacher() {
        System.out.println("\n--------ЗМІНЮЄМО ДАНІ ВИКЛАДАЧА--------");
        try {
        Teacher oldTeacher = service.findTeacherInteractively();
        if (oldTeacher == null) return;
        String newFirstName = UtilityValidation.askInput("Нове ім'я: ");
        String newLastName = UtilityValidation.askInput("Нове прізвище: ");
        String newMiddleName = UtilityValidation.askInput("Нове по батькові: ");
        String newEmail = UtilityValidation.askInput("Новий email: ");
        String newPhone = UtilityValidation.askInput("Новий телефон: ");
        Teacher.TeachersPosition position = UtilityValidation.readEnum(Teacher.TeachersPosition.class,
                "Нова посада (Професор/Доцент/Старший викладач/Викладач/Асистент/Викладач-стажист): ");
        Teacher.TeachersDegree degree = UtilityValidation.readEnum(Teacher.TeachersDegree.class,
                "Новий ступінь (Доктор філософії/Доктор наук/не має): ");
        Teacher.TeachersAcademicTitle title = UtilityValidation.readEnum(Teacher.TeachersAcademicTitle.class,
                "Нове звання (Доцент/Професор/Старший дослідник/не має):");
        double workload = UtilityValidation.readDouble("Нове навантаження (год/рік): ");
        if (teacherRepository.updateTeacher(oldTeacher.getId(), newFirstName, newLastName, newMiddleName,
                newEmail, newPhone, position, degree, title, workload)) {
            service.syncWithFile();
            System.out.println("Дані викладача було оновлено");
        } } catch (UniversityException e) {
            System.out.println("Помилка оновлення: " + e.getMessage());
        }
    }

    private void searchTeacherByPIB() {
        System.out.println("\n--------ПОШУК ВИКЛАДАЧА ЗА ПІБ--------");
        String firstName = askInput("Введіть ім'я: ");
        String lastName = askInput("Введіть прізвище: ");
        String middleName = askInput("Введіть по батькові: ");

        teacherRepository.searchingTeacherByPIB(firstName, lastName, middleName);
    }

    private void alfabetSortFaculty(){
        service.listDTOforFaculties();
        Faculty faculty = service.findFacultyInteractively();
        if (faculty == null) return;
        teacherRepository.printTeachersByFacultyAlphabetically(faculty);
        System.out.println("Викладачів відсортовано.");
    }

    private void printMenu() {
        System.out.println("\n====================== РОБОТА З ВИКЛАДАЧАМИ ======================");
        System.out.println("1. Показати викладачів");
        System.out.println("2. Змінити інформацію про викладача");
        System.out.println("3. Пошук викладача за ПІБ");
        System.out.println("4. Сортування викладача за ПІБ");
        System.out.println("0. Вийти в головне меню");
    }
}
