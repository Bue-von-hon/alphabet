package uhs.alphabet.badge.codeforces;

public class CodeForcesBadge {
    private final String handle;
    private final String color;
    private final CodeforcesBadgeFileStream codeforcesBadgeFileStream;

    public CodeForcesBadge(String handle, String color, CodeforcesBadgeFileStream codeforcesBadgeFileStream) {
        this.handle = handle;
        this.color = color;
        this.codeforcesBadgeFileStream = codeforcesBadgeFileStream;
    }

    public String getBadge() {
        return codeforcesBadgeFileStream.getBadge()
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
