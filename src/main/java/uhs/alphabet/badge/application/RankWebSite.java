package uhs.alphabet.badge.application;

import uhs.alphabet.badge.domain.Website;

public interface RankWebSite {
    String getRank(String handle);
    Website getFrom();
    String getColor(String rank);
}
