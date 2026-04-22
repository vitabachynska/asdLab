package service;

public class Authorization {
    //1234
    private static final String MANAGER_PASSWORD_HASH = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4";
    //4321
    private static final String ADMIN_PASSWORD_HASH = "fe2592b42a727e977f055947385b709cc82b16b9a87f88c6abf3900d65d0cdc3"; // admin

    private static RoleForm currentRole = RoleForm.USER;

    public static boolean can(RoleForm permission) {
        if (currentRole == RoleForm.ADMINISTRATOR) {
            return true;
        }
        return currentRole.hasPermission(permission.getMask()) && Validation.hasRights;
    }

    public static RoleForm getCurrentRole() {
        return currentRole;
    }

    public static void logout() {
        currentRole = RoleForm.USER;
        System.out.println("Ви вийшли з системи. Поточна роль: Користувач");
    }

    public static void loginAsUser() {
        currentRole = RoleForm.USER;
        System.out.println("Ви увійшли як користувач. Доступ обмежено переглядом.");
    }

    public static boolean loginAsManager(String password) {
        if (checkPassword(password, MANAGER_PASSWORD_HASH)) {
            currentRole = RoleForm.MANAGER;
            System.out.println("Авторизацію пройдено: Менеджер");
            return true;
        }
        System.out.println("Неправильний пароль менеджера!");
        return false;
    }

    public static boolean loginAsAdmin(String password) {
        if (checkPassword(password, ADMIN_PASSWORD_HASH)) {
            currentRole = RoleForm.ADMINISTRATOR;
            System.out.println("Авторизацію пройдено: Адміністратор");
            return true;
        }
        System.out.println("Неправильний пароль адміністратора!");
        return false;
    }

    private static boolean checkPassword(String password, String correctHash) {
        String inputHash = HashUtil.getHash(password);
        //System.out.println(inputHash);
        return correctHash.equals(inputHash);
    }


}
