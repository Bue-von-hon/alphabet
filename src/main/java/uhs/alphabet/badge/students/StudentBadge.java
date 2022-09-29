package uhs.alphabet.badge.students;

public class StudentBadge {
    private static final String badge = StudentBadgeFileStream.getBadge();

    public static String of(final String name, final String handle) {
        return badge.replaceAll("\\{(name)}", name).replaceAll("\\{(handle)}", handle);
    }
}
