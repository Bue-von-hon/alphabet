package uhs.alphabet.badge.adapter;

import java.util.HashMap;
import java.util.Map;

public enum CodeForcesRank {
    Legendary_Grandmaster("legendary grandmaster", "url(#grad1)"),
    International_Grandmaster("international grandmaster", "red"),
    Grandmaster("grandmaster", "red"),
    International_Master("international master", "orange"),
    Master("master", "orange"),
    Candidate_Master("candidate master", "violet"),
    Expert("expert", "blue"),
    Specialist("specialist", "cyan"),
    Pupil("pupil", "green"),
    Newbie("newbie", "grey");

    private String rank;
    private String color;
    private static Map<String, String > colorMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put(Legendary_Grandmaster.rank, Legendary_Grandmaster.color);
        colorMap.put(International_Grandmaster.rank, International_Grandmaster.color);
        colorMap.put(Grandmaster.rank, Grandmaster.color);
        colorMap.put(International_Master.rank, International_Master.color);
        colorMap.put(Master.rank, Master.color);
        colorMap.put(Candidate_Master.rank, Candidate_Master.color);
        colorMap.put(Expert.rank, Legendary_Grandmaster.color);
        colorMap.put(Specialist.rank, Specialist.color);
        colorMap.put(Pupil.rank, Pupil.color);
        colorMap.put(Newbie.rank, Newbie.color);
    }

    CodeForcesRank(String rank, String color) {
        this.rank = rank;
        this.color = color;
    }

    public static String getRank2Color(String rank) {
        return colorMap.get(rank);
    }
}
