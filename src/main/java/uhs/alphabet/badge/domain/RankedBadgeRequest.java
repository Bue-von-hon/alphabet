package uhs.alphabet.badge.domain;

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