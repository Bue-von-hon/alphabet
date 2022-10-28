package uhs.alphabet.badge.adapter;

import uhs.alphabet.badge.application.RankWebSite;
import uhs.alphabet.badge.domain.Website;

public class Codeforces900GreyNewbieRankWebSite implements RankWebSite {

    @Override
    public String getRank(String handle) {
        return "Newbie";
    }

    @Override
    public Website getFrom() {
        return Website.CODEFORCES;
    }

    @Override
    public String getColor(String rank) {
        return "grey";
    }
}
