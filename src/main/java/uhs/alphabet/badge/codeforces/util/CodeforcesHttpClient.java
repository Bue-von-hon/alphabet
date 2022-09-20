package uhs.alphabet.badge.codeforces.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uhs.alphabet.badge.codeforces.dto.CodeforcesResponse;

import java.net.URI;
import java.nio.charset.Charset;

@Component
public class CodeforcesHttpClient implements CodeforcesClient {
    private static final String CodeforcesUrl = "https://codeforces.com";
    private static final String UserInfoPath = "/api/user.info";
    private static final Charset charset = Charset.forName("UTF-8");
    @Override
    public CodeforcesResponse getData(String handle) {
        URI uri = UriComponentsBuilder.fromUriString(CodeforcesUrl)
                .path(UserInfoPath)
                .queryParam("handles", handle)
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CodeforcesResponse> response = restTemplate.getForEntity(uri, CodeforcesResponse.class);
        return response.getBody();
    }
}
