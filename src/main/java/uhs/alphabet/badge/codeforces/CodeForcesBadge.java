package uhs.alphabet.badge.codeforces;

import uhs.alphabet.badge.codeforces.dto.CodeforcesResponse;
import uhs.alphabet.badge.codeforces.util.CodeForcesRank;
import uhs.alphabet.badge.codeforces.util.CodeforcesClient;

public class CodeForcesBadge {
    private final String handle;
    private final CodeforcesClient codeforcesClient;

    public CodeForcesBadge(final String handle, final CodeforcesClient codeforcesClient) {
        this.handle = handle;
        this.codeforcesClient = codeforcesClient;
    }

    public String getBadge() {
        CodeforcesResponse codeforcesResponse = codeforcesClient.getCodeforcesResponse(handle);
        String rank = codeforcesResponse.getRank();
        String color = CodeForcesRank.getRank2Color(rank);
        CodeforcesBadgeFileStream codeforcesBadgeFileStream = new CodeforcesBadgeFileStream();
        return codeforcesBadgeFileStream.getBadge()
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
