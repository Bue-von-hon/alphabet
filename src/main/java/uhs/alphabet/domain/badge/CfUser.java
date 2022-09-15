package uhs.alphabet.domain.badge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class CfUser {
    private final String handle;
    private final String color;
    private static final String codeforcesUserInfoUrl = "https://codeforces.com/api/user.info?handles=";
    private static final Charset charset = Charset.forName("UTF-8");
    private static final ObjectMapper mapper = new ObjectMapper();
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
        String rank = null;
        try {
            URL url = new URL(codeforcesUserInfoUrl+handle);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 3초 타임 아웃
            urlConnection.setReadTimeout(3000);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            String s = charset.decode(buffer).toString();
            Map<String, Object> stringObjectMap = mapper.readValue(s, new TypeReference<Map<String, Object>>() {});
            ArrayList<HashMap<String, Object>> result = (ArrayList<HashMap<String, Object>>) stringObjectMap.get("result");
            HashMap<String, Object> map = result.get(0);
            rank = (String) map.get("rank");
            rank = rank.toLowerCase();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String color = colorMap.get(rank);
        if (color == null) color = "grey";

        return new CfUser(handle, color);
    }

    public String getHandle() {
        return handle;
    }

    public String getColor() {
        return color;
    }
}
