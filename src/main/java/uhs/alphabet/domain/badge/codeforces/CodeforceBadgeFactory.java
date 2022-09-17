package uhs.alphabet.domain.badge.codeforces;

import org.springframework.stereotype.Component;
@Component
public class CodeforceBadgeFactory {

    public String getBadge(String handle, String color, CodeforcesBadgeFileStream badgeFileStream) {
        String badge = badgeFileStream.getBadge();
        return badge
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
