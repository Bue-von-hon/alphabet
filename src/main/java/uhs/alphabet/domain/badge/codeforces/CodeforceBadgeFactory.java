package uhs.alphabet.domain.badge.codeforces;

import org.springframework.stereotype.Component;
@Component
public class CodeforceBadgeFactory {

    public String getBadge(String handle, String color, BadgeStream badgeStream) {
        String badge = badgeStream.getBadge();
        return badge
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
