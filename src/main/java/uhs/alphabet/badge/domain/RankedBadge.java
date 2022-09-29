package uhs.alphabet.badge.domain;

public class RankedBadge {
    private final String handle;
    private final String color;
    private final String badge;

    public RankedBadge(String badge, String handle, String color) {
        this.badge = badge;
        this.handle = handle;
        this.color = color;
    }

    public String getBadge() {
        return badge
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
