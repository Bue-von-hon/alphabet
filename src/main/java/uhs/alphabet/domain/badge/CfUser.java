package uhs.alphabet.domain.badge;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.util.*;

public class CfUser {
    private final String handle;
    private final String color;
    private static final Map<String, String> colorMap = new HashMap<>();
    static {
        colorMap.put(CodeForcesRank.Legendary_Grandmaster.getLowerValue(), "url(#grad1)");
        colorMap.put(CodeForcesRank.International_Grandmaster.getLowerValue(), "red");
        colorMap.put(CodeForcesRank.Grandmaster.getLowerValue(), "red");
        colorMap.put(CodeForcesRank.International_Master.getLowerValue(), "orange");
        colorMap.put(CodeForcesRank.Master.getLowerValue(), "orange");
        colorMap.put(CodeForcesRank.Candidata_Master.getLowerValue(), "violet");
        colorMap.put(CodeForcesRank.Expert.getLowerValue(), "blue");
        colorMap.put(CodeForcesRank.Specialist.getLowerValue(), "cyan");
        colorMap.put(CodeForcesRank.Pupil.getLowerValue(), "green");
        colorMap.put(CodeForcesRank.Newbie.getLowerValue(), "grey");
    }

    public CfUser(final String handle, final String color) {
        this.handle = handle;
        this.color = color;
    }

    public static CfUser of(final String handle) {
        String rank = "";
        String data = CodeforcesClient.getData(handle);
        rank = CodeforcesMapper.getRank(data).toLowerCase();

        String color = colorMap.get(rank);
        if (!isExistColor(color)) color = "grey";

        return new CfUser(handle, color);
    }

    private static boolean isExistColor(String color) {
        return color != null;
    }

    public String getHandle() {
        return handle;
    }

    public String getColor() {
        return color;
    }
}
