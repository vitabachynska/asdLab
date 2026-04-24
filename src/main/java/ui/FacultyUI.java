package ui;

import domain.*;
import exceptions.*;
import service.*;

import java.util.List;

public class FacultyUI {
    //InmemoryTeachers teacherRepo = new InmemoryTeachers();
    private Service service;

    public FacultyUI(Service service) {
        this.service = service;
    }

    public void workWithFaculty() {
        try {
            while (true) {
                printMenu();
                int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 4);
                if (choice == 0) break;

                if (!UtilityValidation.isUniversityExist(service)) continue;

                switch (choice) {
                    case 1 -> showFaculties();
                    case 2 -> createFaculty();
                    case 3 -> deleteFaculty();
                    case 4 -> updateFaculty();
                }
            }
        } catch (UniversityException e) {
            System.out.println("\nПОМИЛКА " + e.getMessage());
        }
    }

    private void showFaculties() {
        System.out.println("\n--------ВИВІД СПИСКУ ФАКУЛЬТЕТІВ--------");
        List<Faculty> list = service.getAllFaculties();
        if (list.isEmpty()) {
            System.out.println("Жодного факультету поки не зареєстровано :(");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void createFaculty(){
        //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
        System.out.println("\n--------СТВОРЮЄМО ФАКУЛЬТЕТ--------");
        String code = UtilityValidation.askInput("Введіть код факультету: ");
        String name = UtilityValidation.askInput("Введіть повну назву факультету: ");
        String shortName = UtilityValidation.askInput("Введіть скорочену назву: ");
        String contacts = UtilityValidation.askInput("Введіть контакти: ");
        try {
            service.addFaculty(code, name, shortName, null, contacts);
            service.syncWithFile();
            System.out.println("Факультет було додано");
        } catch (IllegalOperationException e) {
            System.out.println("Не вдалося додати факультет: " + e.getMessage());
        } catch (DataPersistenceException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        } catch (AuthorizationException e) {
            System.out.println(e.getMessage());
        }
    }


    private void deleteFaculty() {
        //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
        System.out.println("\n--------ВИДАЛЯЄМО ФАКУЛЬТЕТ--------");
        try {
            service.listDTOforFaculties();
            Faculty faculty = service.findFacultyInteractively();
            if (faculty == null) return;
            service.deleteFaculty(faculty.getName());
            service.syncWithFile();
            System.out.println("Факультет видалено");

        } catch (AuthorizationException e) {
            System.out.println("Помилка доступу: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Помилка даних: " + e.getMessage());
        } catch (DataPersistenceException e) {
            System.out.println("Помилка збереження у файл: " + e.getMessage());
        } catch (IllegalOperationException e) {
            System.out.println("Не вдалося видалити факультет: " + e.getMessage());
        }

    }

    private void updateFaculty() {
        //        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
//            throw new AuthorizationException("Помилка: Потрібні права менеджера або відкритий доступ до них");
//        }
        System.out.println("\n--------ОНОВЛЮЄМО ФАКУЛЬТЕТ--------");
        try {
            service.listDTOforFaculties();
            Faculty oldFaculty = service.findFacultyInteractively();
            if (oldFaculty == null) return;

            String newCode = UtilityValidation.askInput("Новий код: ");
            String newName = UtilityValidation.askInput("Нова повна назва: ");
            String newShortName = UtilityValidation.askInput("Нова коротка назва: ");
            String newContacts = UtilityValidation.askInput("Нові контакти: ");

            Teacher newDean = service.findDeanInteractively();
            if (newDean == null) return;
            service.updateFaculty(oldFaculty.getName(), newCode, newName, newShortName, newDean, newContacts);
            service.syncWithFile();
            System.out.println("ФАКУЛЬТЕТ УСПІШНО ОНОВЛЕНО");

        } catch (AuthorizationException e) {
            System.out.println("Відмовлено в доступі: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Помилка оновлення: " + e.getMessage());
        } catch (DataPersistenceException e) {
            System.out.println("Не вдалося зберегти зміни у файл: " + e.getMessage());
        } catch (IllegalOperationException e ){
            System.out.println("Помилка при оновленні: " + e.getMessage());
        }
    }

    private boolean checkRights() {
        if (!Authorization.can(RoleForm.MANAGER) && !Validation.hasRights) {
            System.out.println("Помилка: Потрібні права менеджера");
            return false;
        }
        return true;
    }

    private void printMenu() {
        System.out.println("\n======================РОБОТА З ФАКУЛЬТЕТОМ======================");
        System.out.println("1. показати факультети\n2. створити факультет\n3. видалити факультет\n4. змінити інформацію\n0. вийти\n");
    }
}
