package uhs.alphabet.badge.codeforces.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import uhs.alphabet.badge.codeforces.CodeforcesResponse;

@Component
public class CodeforcesMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public String getRank(String data) {
        CodeforcesResponse codeforcesResponse;
        try {
            codeforcesResponse = mapper.readValue(data, CodeforcesResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        return codeforcesResponse.getResult().get(0).getRank().toLowerCase();
    }
}
