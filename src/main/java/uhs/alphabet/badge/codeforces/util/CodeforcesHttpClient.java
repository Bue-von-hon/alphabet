package uhs.alphabet.badge.codeforces.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uhs.alphabet.badge.codeforces.dto.CodeforcesResponse;

import java.net.URI;

@Component
public class CodeforcesHttpClient implements CodeforcesClient {
    private static final String CODEFORCES_URL = "https://codeforces.com";
    private static final String USER_INFO_PATH = "/api/user.info";
    @Override
    public CodeforcesResponse getData(String handle) {
        URI uri = UriComponentsBuilder.fromUriString(CODEFORCES_URL)
                .path(USER_INFO_PATH)
                .queryParam("handles", handle)
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CodeforcesResponse> response = restTemplate.getForEntity(uri, CodeforcesResponse.class);
        return response.getBody();
    }
}
