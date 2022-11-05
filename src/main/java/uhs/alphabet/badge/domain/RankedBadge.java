package uhs.alphabet.badge.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import uhs.alphabet.badge.application.RankedBadgeFile;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RankedBadge {

    private final String badge;
    private final String handle;
    private final String color;

    public static String getBadge(RankedBadgeFile rankedBadgeFile, RankedBadgeData badgeData) {
        RankedBadge badge = getRankedBadge(rankedBadgeFile, badgeData);
        return badge.replaceHandleAndColor();
    }

    static RankedBadge getRankedBadge(RankedBadgeFile rankedBadgeFile,
        RankedBadgeData badgeData) {
        return new RankedBadge(rankedBadgeFile.getBadge(), badgeData.getHandle(),
            badgeData.getColor());
    }

    final String replaceHandleAndColor() {
        return badge
            .replaceAll("\\{(handle)}", handle)
            .replaceAll("\\{(color)}", color);
    }
}
