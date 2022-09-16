package uhs.alphabet.domain.badge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CodeforcesMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String getRank(String data) {
        Map<String, Object> stringObjectMap;
        try {
            stringObjectMap = mapper.readValue(data, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        ArrayList<HashMap<String, Object>> result = (ArrayList<HashMap<String, Object>>) stringObjectMap.get("result");
        HashMap<String, Object> map = result.get(0);
        return (String) map.get("rank");
    }
}
