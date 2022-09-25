package uhs.alphabet.badge.students;

public class Student {
    private final String name;
    private final String handle;

    public Student(String name, String handle) {
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
