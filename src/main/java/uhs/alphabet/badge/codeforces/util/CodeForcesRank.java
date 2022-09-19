package uhs.alphabet.badge.codeforces.util;

import java.util.HashMap;
import java.util.Map;

public enum CodeForcesRank {
    Legendary_Grandmaster("Legendary Grandmaster", "url(#grad1)"),
    International_Grandmaster("International Grandmaster", "red"),
    Grandmaster("Grandmaster", "red"),
    International_Master("International Master", "orange"),
    Master("Master", "orange"),
    Candidata_Master("Candidata Master", "violet"),
    Expert("Expert", "blue"),
    Specialist("Specialist", "cyan"),
    Pupil("Pupil", "green"),
    Newbie("Newbie", "grey");

    private String rank;
    private String color;
    private static Map<String, String > colorMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put(Legendary_Grandmaster.rank.toLowerCase(), Legendary_Grandmaster.color);
        colorMap.put(International_Grandmaster.rank.toLowerCase(), International_Grandmaster.color);
        colorMap.put(Grandmaster.rank.toLowerCase(), Grandmaster.color);
        colorMap.put(International_Master.rank.toLowerCase(), International_Master.color);
        colorMap.put(Master.rank.toLowerCase(), Master.color);
        colorMap.put(Candidata_Master.rank.toLowerCase(), Candidata_Master.color);
        colorMap.put(Expert.rank.toLowerCase(), Legendary_Grandmaster.color);
        colorMap.put(Specialist.rank.toLowerCase(), Specialist.color);
        colorMap.put(Pupil.rank.toLowerCase(), Pupil.color);
        colorMap.put(Newbie.rank.toLowerCase(), Newbie.color);
    }

    CodeForcesRank(String rank, String color) {
        this.rank = rank;
        this.color = color;
    }

    public static String getRank2Color(String rank) {
        return colorMap.get(rank);
    }
}
