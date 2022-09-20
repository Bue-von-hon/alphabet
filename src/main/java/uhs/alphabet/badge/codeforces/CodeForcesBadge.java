package uhs.alphabet.badge.codeforces;

import uhs.alphabet.badge.codeforces.util.CodeForcesRank;
import uhs.alphabet.badge.codeforces.util.CodeforcesClient;
import uhs.alphabet.badge.codeforces.util.CodeforcesMapper;

public class CodeForcesBadge {
    private final String handle;
    private final CodeforcesMapper codeforcesMapper;
    private final CodeforcesClient codeforcesClient;

    public CodeForcesBadge(String handle, CodeforcesMapper codeforcesMapper, CodeforcesClient codeforcesClient) {
        this.handle = handle;
        this.codeforcesMapper = codeforcesMapper;
        this.codeforcesClient = codeforcesClient;
    }

    public String getBadge() {
        String data = codeforcesClient.getData(handle);
        String rank = codeforcesMapper.getRank(data);
        String color = CodeForcesRank.getRank2Color(rank);
        CodeforcesBadgeFileStream codeforcesBadgeFileStream = new CodeforcesBadgeFileStream();
        return codeforcesBadgeFileStream.getBadge()
                .replaceAll("\\{(handle)}", handle)
                .replaceAll("\\{(color)}", color);
    }
}
