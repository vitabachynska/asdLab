package ui;

import domain.*;
import exceptions.*;
import repository.InmemoryStudents;
import service.*;

import java.time.LocalDate;

import static service.UtilityValidation.*;

public class DepartmentsUI {
    private Service service;
    InmemoryStudents inmemoryStudents = new InmemoryStudents();

    public DepartmentsUI(Service service) {
        this.service = service;
    }

    public void workWithDepartment(){
        try {
            while (true) {
                printMenu();
                int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 8);
                if (choice == 0) break;
                if (!UtilityValidation.isUniversityExist(service)) continue;

                switch (choice) {
                    case 1 -> showDepartments();
                    case 2 -> createDepartment();
                    case 3 -> deleteDepartment();
                    case 4 -> updateDepartment();
                    case 5 -> addTeacherToDept();
                    case 6 -> addStudentToDept();
                    case 7 -> deleteTeacherFromDept();
                    case 8 -> deleteStudentFromDept();
                }
            }} catch (UniversityException e) {
                System.out.println("Помилка: " + e.getMessage());}
    }

    private void showDepartments() {
        System.out.println("\n--------ВИВІД СПИСКУ КАФЕДР--------");
        if (service.getAllDepartments().isEmpty()) {
            System.out.println("Жодної кафедри поки не зареєстровано :(");
        } else {
            service.getAllDepartments().forEach(System.out::println);
        }
    }

    private void createDepartment(){
        //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
        System.out.println("\n--------СТВОРЮЄМО КАФЕДРУ--------");
        try {
            String code = UtilityValidation.askInput("Код кафедри: ");
            String name = UtilityValidation.askInput("Назва кафедри: ");
            String loc = UtilityValidation.askInput("Локація: ");
            service.listDTOforFaculties();
            Faculty faculty = service.findFacultyInteractively();
            if (faculty == null) return;
            service.addDepartment(faculty.getName(), code, name, null, loc);
            service.syncWithFile();
            System.out.println("КАФЕДРУ УСПІШНО ДОДАНО ДО ФАКУЛЬТЕТУ: " + faculty.getName());

        } catch (AuthorizationException e) {
            System.out.println("Помилка доступу: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Помилка даних: " + e.getMessage());
        } catch (DataPersistenceException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        } catch (IllegalOperationException e) {
            System.out.println("Помилка: " + e.getMessage());
        }}

        private void deleteDepartment () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ВИДАЛЯЄМО КАФЕДРУ--------");
            try {
                service.listDTOforFaculties();
                Faculty faculty = service.findFacultyInteractively();
                if (faculty == null) return;
                Department dept = service.findDepartmentInteractively(faculty);
                if (dept == null) return;
                service.deleteDepartment(faculty.getName(), dept.getName());
                service.syncWithFile();
                System.out.println("Кафедру '" + dept.getName() + "' успішно видалено");

            } catch (AuthorizationException e) {
                System.out.println("Помилка доступу: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка даних: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка запису у файл: " + e.getMessage());
            } catch (IllegalOperationException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        private void updateDepartment () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ОНОВЛЮЄМО КАФЕДРУ--------");
            try {
                service.listDTOforFaculties();
                Faculty faculty = service.findFacultyInteractively();
                if (faculty == null) return;
                Department oldDept = service.findDepartmentInteractively(faculty);
                if (oldDept == null) return;
                String oldName = oldDept.getName();
                String newCode = UtilityValidation.askInput("Введіть новий код кафедри: ");
                String newName = UtilityValidation.askInput("Введіть нову назву кафедри: ");
                String newLoc = UtilityValidation.askInput("Введіть нову локацію: ");

                Teacher head = service.findHeadInteractively();
                if (head == null) return;
                service.updateDepartment(faculty.getName(), oldName, newCode, newName, head, newLoc);
                service.syncWithFile();
                System.out.println("КАФЕДРУ УСПІШНО ОНОВЛЕНО");
            } catch (AuthorizationException e) {
                System.out.println("Відмовлено в доступі: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка даних: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка збереження у файл: " + e.getMessage());
            } catch (IllegalOperationException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        private void addTeacherToDept () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ДОДАЄМО ВИКЛАДАЧА--------");
            try {
                service.listDTOforFaculties();
                Faculty faculty = service.findFacultyInteractively();
                if (faculty == null) return;
                Department dept = service.findDepartmentInteractively(faculty);
                if (dept == null) return;
                String id = UtilityValidation.askInput("ID: ");
                String name = UtilityValidation.askInput("Ім'я: ");
                String lName = UtilityValidation.askInput("Прізвище: ");
                String mName = UtilityValidation.askInput("По батькові: ");

                LocalDate birth = UtilityValidation.readDate("Дата народження (РРРР-ММ-ДД): ");
                String email = UtilityValidation.askInput("Email: ");
                String phone = UtilityValidation.askInput("Телефон: ");

                Teacher.TeachersPosition pos = readEnum(Teacher.TeachersPosition.class,
                        "Посада (Професор/Доцент/Старший викладач/Викладач/Асистент/Викладач-стажист): ");
                Teacher.TeachersDegree deg = readEnum(Teacher.TeachersDegree.class,
                        "Ступінь (Доктор філософії/Доктор наук/не має): ");
                Teacher.TeachersAcademicTitle title = readEnum(Teacher.TeachersAcademicTitle.class,
                        "Звання (Доцент/Професор/Старший дослідник/не має): ");

                LocalDate hire = readDate("Дата працевлаштування: ");
                double workload = readDouble("Навантаження (год/рік): ");
                Teacher teacher = new Teacher(id, name, lName, mName, birth, email, phone, pos, deg, title, hire, workload, faculty, dept);
                service.addTeacher(faculty.getName(), dept.getName(), teacher);
                service.syncWithFile();
                System.out.println("ВИКЛАДАЧА УСПІШНО ДОДАНО ТА ЗАРЕЄСТРОВАНО");

            } catch (AuthorizationException e) {
                System.out.println("Помилка доступу: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка структури: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка збереження даних: " + e.getMessage());
            } catch (IllegalOperationException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        private void addStudentToDept () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ДОДАЄМО СТУДЕНТА--------");
            try {
                service.listDTOforFaculties();
                Faculty faculty = service.findFacultyInteractively();
                if (faculty == null) return;

                Department dept = service.findDepartmentInteractively(faculty);
                if (dept == null) return;

                String id = UtilityValidation.askInput("ID: ");
                String name = UtilityValidation.askInput("Ім'я: ");
                String lName = UtilityValidation.askInput("Прізвище: ");
                String mName = UtilityValidation.askInput("По батькові: ");
                LocalDate birth = readDate("Дата народження: ");
                String email = UtilityValidation.askInput("Email: ");
                String phone = UtilityValidation.askInput("Телефон: ");
                String card = UtilityValidation.askInput("ID картки: ");

                int course = UtilityValidation.readInt("Курс (1-4): ", 1, 4);
                int group = UtilityValidation.readInt("Група (1-6): ", 1, 6);
                int year = readYear("Рік вступу: ");

                Student.TuitionForm form = readEnum(Student.TuitionForm.class, "Форма (Бюджет/Контракт): ");
                Student.StudentStatus status = readEnum(Student.StudentStatus.class, "Статус (Навчається/В академ відпустці/Відрахований): ");
                Student student = new Student(id, name, lName, mName, birth, email, phone, card, course, group, year, form, status, faculty, dept);
                service.addStudent(faculty.getName(), dept.getName(), student);
                service.syncWithFile();
                System.out.println("СТУДЕНТА УСПІШНО ЗАРЕЄСТРОВАНО");

            } catch (AuthorizationException e) {
                System.out.println("Помилка доступу: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка структури: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка збереження даних: " + e.getMessage());
            } catch (IllegalOperationException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        private void deleteTeacherFromDept () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ВИДАЛЯМО ВИКЛАДАЧА--------");
            try {
                service.listDTOforTeacher();
                String id = UtilityValidation.askInput("Введіть ID викладача для видалення: ");
                service.deleteTeacher(id);
                service.syncWithFile();
                System.out.println("Викладача успішно видалено");

            } catch (AuthorizationException e) {
                System.out.println("Відмовлено в доступі: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка запису у файл: " + e.getMessage());
            }
        }

        private void deleteStudentFromDept () {
            //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
            System.out.println("\n--------ВИДАЛЯЄМО СТУДЕНТА--------");
            try {
                service.listDTOforStudents();
                String id = UtilityValidation.askInput("Введіть ID студента для видалення: ");
                service.deleteStudent(id);
                service.syncWithFile();
                System.out.println("Студента успішно видалено");

            } catch (AuthorizationException e) {
                System.out.println("Відмовлено в доступі: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println("Помилка: " + e.getMessage());
            } catch (DataPersistenceException e) {
                System.out.println("Помилка запису у файл: " + e.getMessage());
            }
        }


        private void printMenu () {
            System.out.println("\n======================РОБОТА З КАФЕДРОЮ======================");
            System.out.println("1. показати кафедри\n2. створити кафедру\n3. видалити кафедру\n4. змінити інформацію");
            System.out.println("5. додати викладача\n6. додати студента\n7. видалити викладача\n8. видалити студента\n0. вийти");
        }
}

