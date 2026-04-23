package ui;

import domain.*;
import service.*;

import java.util.List;

public class FacultyUI {
    //InmemoryTeachers teacherRepo = new InmemoryTeachers();
    private Service service;

    public FacultyUI(Service service) {
        this.service = service;
    }

    public void workWithFaculty() {
        while (true) {
            printMenu();
            int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 4);
            if (choice == 0) break;

            if (!UtilityValidation.isUniversityExist(service)) continue;

            switch (choice) {
                case 1 -> showFaculties();
                case 2 -> {if (Authorization.can(RoleForm.EDIT.getMask())) createFaculty();
                           else System.out.println("У вас немає прав на створення факультетів");}
                case 3 -> {if (Authorization.can(RoleForm.EDIT.getMask())) deleteFaculty();
                        else System.out.println("У вас немає прав на видалення факультетів");}
                case 4 -> {if (Authorization.can(RoleForm.EDIT.getMask())) updateFaculty();
                        else System.out.println("У вас немає прав на оновлення факультетів");}
            }
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

    private void createFaculty() {
   //     if (!checkRights()) return;
        System.out.println("\n--------СТВОРЮЄМО ФАКУЛЬТЕТ--------");
        String code = UtilityValidation.askInput("Введіть код факультету: ");
        String name = UtilityValidation.askInput("Введіть повну назву факультету: ");
        String shortName = UtilityValidation.askInput("Введіть скорочену назву: ");
        String contacts = UtilityValidation.askInput("Введіть контакти: ");

        service.addFaculty(code, name, shortName, null, contacts);
        service.syncWithFile();
        System.out.println("Факультет було додано");
    }

    private void deleteFaculty() {
        System.out.println("\n--------ВИДАЛЯЄМО ФАКУЛЬТЕТ--------");
    //    if (!checkRights()) return;
        service.listDTOforFaculties();
        Faculty faculty = service.findFacultyInteractively();
        if (faculty == null) return;
        if (service.deleteFaculty(faculty.getName())) {
            service.syncWithFile();
            System.out.println("Факультет видалено");
        } else {
            System.out.println("Факультет не знайдено");
        }
    }

    private void updateFaculty() {
        System.out.println("\n--------ОНОВЛЮЄМО ФАКУЛЬТЕТ--------");
        // if (!checkRights()) return;
        service.listDTOforFaculties();
        Faculty oldFaculty = service.findFacultyInteractively();
        if (oldFaculty == null) return;
        String newCode = UtilityValidation.askInput("Новий код: ");
        String newName = UtilityValidation.askInput("Нова повна назва: ");
        String newShortName = UtilityValidation.askInput("Нова коротка назва: ");
        String newContacts = UtilityValidation.askInput("Нові контакти: ");
        Teacher newDean = service.findDeanInteractively();
        if (newDean == null) return;
        if (service.updateFaculty(oldFaculty.getName(), newCode, newName, newShortName, newDean, newContacts)) {
            service.syncWithFile();
            System.out.println("ФАКУЛЬТЕТ УСПІШНО ОНОВЛЕНО");
        } else {
            System.out.println("Помилка: Не вдалося оновити факультет у базі.");
        }
    }

    private boolean checkRights() {
        if (!Authorization.can(RoleForm.MANAGER.getMask()) && !Validation.hasRights) {
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
