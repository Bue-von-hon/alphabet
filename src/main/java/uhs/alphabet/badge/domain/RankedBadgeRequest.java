package uhs.alphabet.badge.domain;

import uhs.alphabet.badge.domain.Website;

public class RankedBadgeRequest {
    private final Website web;
    private final String handle;

    public RankedBadgeRequest(Website web, String handle) {
        this.web = web;
        this.handle = handle;
    }

    public Website getWeb() {
        return web;
    }

    public String getHandle() {
        return handle;
    }
}
