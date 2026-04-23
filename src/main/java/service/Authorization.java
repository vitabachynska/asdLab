package service;

public class Authorization {
    private static String managerPasswordHash = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4";
    private static  String adminPasswordHash = "fe2592b42a727e977f055947385b709cc82b16b9a87f88c6abf3900d65d0cdc3";

    private static RoleForm currentRole = RoleForm.USER;

    // Тепер can приймає маску (наприклад, RoleForm.EDIT.getMask())
    public static boolean can(int permissionMask) {
        return currentRole.hasPermission(permissionMask);
    }
    public static boolean loginAsUser() {
            currentRole = RoleForm.USER;
            return true;

    }
    public static boolean loginAsManager(String password) {
        if (checkPassword(password, managerPasswordHash)) {
            currentRole = RoleForm.MANAGER;
            return true;
        }
        return false;
    }

    public static boolean loginAsAdmin(String password) {
        if (checkPassword(password, adminPasswordHash)) {
            currentRole = RoleForm.ADMINISTRATOR;
            return true;
        }
        return false;
    }
    public static void setManagerPassword(String newPassword) {
        managerPasswordHash = HashUtil.getHash(newPassword);
        System.out.println("Пароль менеджера успішно змінено");
    }

    // Метод для зміни пароля адміністратора
    public static void setAdminPassword(String newPassword) {
        adminPasswordHash = HashUtil.getHash(newPassword);
        System.out.println("Пароль адміністратора успішно змінено");
    }

    public static void logout() {
        currentRole = RoleForm.USER;
        System.out.println("Ви вийшли. Поточна роль: " + currentRole.getLabel());
    }

    private static boolean checkPassword(String password, String correctHash) {
        return password != null && HashUtil.getHash(password).equals(correctHash);
    }

    public static RoleForm getCurrentRole() {
        return currentRole;
    }
}