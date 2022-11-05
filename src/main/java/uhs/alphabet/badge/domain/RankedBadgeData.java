package uhs.alphabet.badge.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uhs.alphabet.badge.application.RankWebSite;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RankedBadgeData {

    private final String handle;
    private final String color;

    public static RankedBadgeData of(RankedBadgeRequest request, RankWebSite webSite) {
        String handle = getHandle(request);
        String color = getColor(webSite, handle);
        return new RankedBadgeData(handle, color);
    }

    static String getHandle(RankedBadgeRequest request) {
        return request.getHandle();
    }

    static String getColor(RankWebSite rankWebSite, String handle) {
        String rank = rankWebSite.getRank(handle);
        return rankWebSite.getColor(rank);
    }
}
