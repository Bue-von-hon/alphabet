package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeForcesBadge;
import uhs.alphabet.badge.codeforces.CodeforcesBadgeFileStream;
import uhs.alphabet.badge.codeforces.util.CodeForcesRank;
import uhs.alphabet.badge.codeforces.util.CodeforcesHttpClient;
import uhs.alphabet.badge.codeforces.util.CodeforcesMapper;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final CodeforcesHttpClient codeforcesClient;
    private final CodeforcesMapper codeforcesMapper;
    public StudentBadgeUser searchStudent(String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }

    public String  makeCodeforcesBadge(String handle) {
        String data = codeforcesClient.getData(handle);
        String rank = codeforcesMapper.getRank(data);
        CodeforcesBadgeFileStream badgeFileStream = new CodeforcesBadgeFileStream();
        CodeForcesBadge badge = new CodeForcesBadge(handle, CodeForcesRank.getRank2Color(rank), badgeFileStream);
        return badge.getBadge();
    }
}
