package service;


import domain.Faculty;

import java.util.List;
//доробити
public class FacultyService{
    Service service = new Service();
    public void workWithFaculty() {
        while (true) {
          //  printFacultyMenu();
            int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 4);

            if (choice == 0) return;

        // 1. Спільна перевірка для всіх пунктів, окрім виходу
        if (this.service.getUniversity() == null) {
        System.out.println("Помилка: Спочатку створіть університет!");
            continue;}

        switch (choice) {
        case 1 -> showAllFaculties();
        case 2 -> addFacultyAction();
        case 3 -> deleteFacultyAction();
        case 4 -> updateFacultyAction();
        case 0 -> {
                System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
                return;
            }
        }
                }
                }

    private void deleteFacultyAction() {
        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights)  return;
        if(!UtilityValidation.isUniversityExist()) return;
        System.out.println("\n--------ВИДАЛЕННЯ ФАКУЛЬТЕТУ--------");
        String facultyName = UtilityValidation.askInput("Введіть назву факультету для видалення : ");
        if (service.deleteFaculty(facultyName)) {
            System.out.println("Факультет видалено");
        } else {
            System.out.println("Факультет з такою назвою не знайдено");
        }
    }


    private void showAllFaculties(){
        if(!UtilityValidation.isUniversityExist()) return;
            System.out.println("\n--------ВИВІД СПИСКУ ФАКУЛЬТЕТІВ--------");
            List<Faculty> list = service.getAllFaculties();
            if (list.isEmpty()) {
                System.out.println("Жодного факультету поки не зареєстровано :(");
            } else {
                list.forEach(System.out::println);
            }

            System.out.println("\n--------ФАКУЛЬТЕТИ--------\n------------------------");
    }


private void addFacultyAction() {
    if (!checkManagerRights()) return;
    if(!UtilityValidation.isUniversityExist()) return;
    System.out.println("\n--------СТВОРЮЄМО ФАКУЛЬТЕТ--------");
    this.service.addFaculty(UtilityValidation.askInput("Введіть код факультету : "), UtilityValidation.askInput("Введіть повну назву факультету : "),
            UtilityValidation.askInput("Введіть коротку назву (абревіатуру) : "), null, UtilityValidation.askInput("Введіть контакти (email/phone) : "));
    System.out.println("ФАКУЛЬТЕТ ДОДАНО");
}

private void updateFacultyAction() {
    if (!checkManagerRights()) return;
    if(!UtilityValidation.isUniversityExist()) return;
    System.out.println("\n--------ЗМІНА ДАНИХ ПРО ФАКУЛЬТЕТ--------");
    String oldName = UtilityValidation.askInput("Введіть стару повну назву факультету : ");

    String newCode = UtilityValidation.askInput("Введіть новий код факультету : ");
    String newName = UtilityValidation.askInput("Введіть нову повну назву факультету : ");
    String newShortName = UtilityValidation.askInput("Введіть нову коротку назву (абревіатуру) : ");
    String newContacts = UtilityValidation.askInput("Введіть нові контакти (email/phone) : ");



   // Teacher newDean = findDeanFlow();
   // if (newDean == null) return;

//    boolean success = this.service.updateFaculty(oldName,
//            UtilityValidation.askInput("Новий код: "),
//            UtilityValidation.askInput("Нова назва: "),
//            UtilityValidation.askInput("Нова абревіатура: "),
//            newDean,
//            UtilityValidation.askInput("Нові контакти: "));

 //   System.out.println(success ? "ЗМІНЕНО" : "ПОМИЛКА: Факультет не знайдено");
}

// --- ДОПОМІЖНІ "ЧИСТІ" МЕТОДИ ---

private boolean checkManagerRights() {
    if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights)  return true;
    System.out.println("Помилка: Потрібні права менеджера");
    return false;
}

//private Teacher findDeanFlow() {
//    while (true) {
//        System.out.println("--- Пошук декана ---");
//        String fName = UtilityValidation.askInput("Ім'я: ");
//        String lName = UtilityValidation.askInput("Прізвище: ");
//        String mName = UtilityValidation.askInput("По батькові: ");
//
//        Teacher t = this.inmemoryTeachers.deanFindByPIB(fName, lName, mName);
//        if (t != null) return t;
//
//        System.out.println("Викладача не знайдено. Спробувати ще раз? (д/н)");
//        if (scanner.nextLine().equalsIgnoreCase("н")) return null;
//    }
//}
}
