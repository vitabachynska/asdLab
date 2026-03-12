package service;

public class Authorization {
    //private String login;
    private Authorization.RoleForm tuitionForm;

    private static final String MANAGER_PASSWORD_HASH = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4";
    private static RoleForm currentRole = RoleForm.USER;

    public enum RoleForm {
        USER("користувач"),
        MANAGER("менеджер");

        private final String label;

        RoleForm(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static Authorization.RoleForm fromString(String text) {
            for (Authorization.RoleForm form : Authorization.RoleForm.values()) {
                if (form.label.equalsIgnoreCase(text.trim()))
                    return form;
            }
            throw new IllegalArgumentException("Введіть правильно роль");
        }
    }

    public static void login(RoleForm role) { currentRole = role; }
    public static RoleForm getCurrentRole() { return currentRole; }
    public static boolean hasRole(RoleForm role) { return currentRole == role; }

    public static void loginAsUser() {
        currentRole = RoleForm.USER;
        System.out.println("Ви увійшли як користувач. Доступ обмежено");
    }
    public static boolean loginAsManager(String password) {

        String inputHash = HashUtil.getHash(password);
        //System.out.println(inputHash);

        if (MANAGER_PASSWORD_HASH.equals(inputHash)) {
            currentRole = RoleForm.MANAGER;
            System.out.println("Ви успішно увійшли як Менеджер");
            return true;
        }
        System.out.println("Неправильний пароль! Вхід автоматично за користувача");
        return false;
    }
}
