package uhs.alphabet.badge.domain;

import lombok.Getter;

@Getter
public class RankedBadgeData {

    private String handle;
    private String coloe;

    public RankedBadgeData(String handle, String coloe) {
        this.handle = handle;
        this.coloe = coloe;
    }
}
