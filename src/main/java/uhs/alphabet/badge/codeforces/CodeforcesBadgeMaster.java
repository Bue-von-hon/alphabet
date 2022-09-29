package uhs.alphabet.badge.codeforces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uhs.alphabet.badge.application.CodeforcesResponse;
import uhs.alphabet.badge.application.CodeForcesRank;
import uhs.alphabet.badge.codeforces.util.CodeforcesClient;

@Component
public class CodeforcesBadgeMaster {
    @Autowired
    private CodeforcesClient codeforcesClient;
    @Autowired
    private CodeForcesBadge codeForcesBadge;

    public String getBadgeByHandle(String handle) {
        CodeforcesResponse codeforcesResponse = codeforcesClient.getCodeforcesResponse(handle);
        String rank = codeforcesResponse.getRank();
        String color = CodeForcesRank.getRank2Color(rank);
        return codeForcesBadge.getBadge(handle, color);
    }
}
