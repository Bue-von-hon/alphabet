package uhs.alphabet.badge.application.codeforces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uhs.alphabet.badge.domain.Website;
import uhs.alphabet.badge.adapter.RankWebSite;

import java.net.URI;

@Service
public class CodeforcesSite implements RankWebSite {
    private static final String CODEFORCES_URL = "https://codeforces.com";
    private static final String USER_INFO_PATH = "/api/user.info";
    @Override
    public String getRank(String handle) {
        URI uri = UriComponentsBuilder.fromUriString(CODEFORCES_URL)
                .path(USER_INFO_PATH)
                .queryParam("handles", handle)
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CodeforcesResponse> response = restTemplate.getForEntity(uri, CodeforcesResponse.class);
        CodeforcesResponse body = response.getBody();
        return body.getRank();
    }

    @Override
    public Website getFrom() {
        return Website.CODEFORCES;
    }

    @Override
    public String getColor(String rank) {
        return CodeForcesRank.getRank2Color(rank);
    }
}
