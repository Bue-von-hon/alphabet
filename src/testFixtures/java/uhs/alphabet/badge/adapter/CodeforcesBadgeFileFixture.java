package uhs.alphabet.badge.adapter;

import uhs.alphabet.badge.application.RankedBadgeFile;
import uhs.alphabet.badge.domain.Website;

public class CodeforcesBadgeFileFixture implements RankedBadgeFile {

    @Override
    public String getBadge() {
        return "handle={handle}, color={color}";
    }

    @Override
    public Website getFrom() {
        return Website.CODEFORCES;
    }
}
