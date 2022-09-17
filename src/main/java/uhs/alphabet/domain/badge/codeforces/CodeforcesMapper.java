package uhs.alphabet.domain.badge.codeforces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CodeforcesMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static String getRank(String data) {
        CodeforcesResponse codeforcesResponse;
        try {
            codeforcesResponse = mapper.readValue(data, CodeforcesResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        return codeforcesResponse.getResult().get(0).getRank().toLowerCase();
    }
}
