package service;

public enum RoleForm {
    VIEW(null, 1 << 0),   // 0001 (1)
    EDIT(null, 1 << 1),   // 0010 (2)
    ADMIN(null, 1 << 2),  // 0100 (4)

    USER("користувач", 1 << 0), // Те саме що VIEW
    MANAGER("менеджер", (1 << 0) | (1 << 1)), // VIEW | EDIT
    ADMINISTRATOR("адміністратор", (1 << 0) | (1 << 1) | (1 << 2)); // VIEW | EDIT | ADMIN

    private final String label;
    private final int mask;

    RoleForm(String label, int mask) {
        this.label = label;
        this.mask = mask;
    }

    public String getLabel() {
        return label;
    }

    public int getMask() {
        return mask;
    }

    public boolean hasPermission(int permissionMask) {
        return (this.mask & permissionMask) == permissionMask;
    }

    public static RoleForm fromString(String text) {
        for (RoleForm form : RoleForm.values()) {
            if (form.label != null && form.label.equalsIgnoreCase(text.trim())) {
                return form;
            }
        }
        throw new IllegalArgumentException("Введіть правильно роль");
    }
}
