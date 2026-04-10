package service;

public class UniversityService {
    Service service = new Service();

public void workWithUniversity() {
    printUniMenu();
    while (true) {
        int choice = UtilityValidation.readInt("==== ОБЕРІТЬ ПУНКТ ====", 0, 2);
        switch (choice) {
            case 1 -> createUniversity();
            case 2 -> showUniversity();
            case 0 -> {
                System.out.println("==ПОВЕРНЕННЯ ДО МЕНЮ==");
                return;

            }
        }
        printUniMenu();
    }
}

    private void createUniversity() {
        if (!Authorization.can(RoleForm.MANAGER)&& !Validation.hasRights) {
            System.out.println("Помилка: Потрібні права менеджера або відкритий доступ до них");
            return;
        }
        System.out.println("\n--------СТВОРЕННЯ УНІВЕРСИТЕТУ--------");
        String fullName = UtilityValidation.askInput("Введіть назву університету: ");
        String shortName = UtilityValidation.askInput("Введіть скорочену назву університету: ");
        String city = UtilityValidation.askInput("Введіть місто: ");
        String address = UtilityValidation.askInput("Введіть адресу: ");

        this.service.addUniversity(fullName, shortName, city, address);
        service.syncWithFile();
        System.out.println("УНІВЕРСИТЕТ БУЛО ДОДАНО");
    }

    private void showUniversity() {
        System.out.println("\n--------УНІВЕРСИТЕТ--------");
        var university = this.service.getUniversity();
        if (university == null) {
            System.out.println("Університет поки не зареєстровано :(");
        } else {
            System.out.println(university);
        }
    }

    private void printUniMenu() {
        System.out.println("\n======================РОБОТА З УНІВЕРСИТЕТОМ======================");
        System.out.println("1. створити університет\n2. показати університети\n0. вийти\n");
    }


}

