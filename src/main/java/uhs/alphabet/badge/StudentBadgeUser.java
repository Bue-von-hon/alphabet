package uhs.alphabet.badge;

public class StudentBadgeUser {
    private final String name;
    private final String handle;

    public StudentBadgeUser(String name, String handle) {
        this.name = name;
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }
}
