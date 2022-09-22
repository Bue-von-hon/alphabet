package uhs.alphabet.badge.codeforces;

import org.springframework.stereotype.Component;

@Component
public class CodeForcesBadge {
    private static CodeforcesBadgeFileStream codeforcesBadgeFileStream = new CodeforcesBadgeFileStream();
    private static final String badge = codeforcesBadgeFileStream.getBadge();
    public String getBadge(String handle, String color) {
        return badge
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
