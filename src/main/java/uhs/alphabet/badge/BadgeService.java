package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeForcesBadge;
import uhs.alphabet.badge.codeforces.util.CodeforcesHttpClient;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final CodeforcesHttpClient codeforcesClient;
    public StudentBadgeUser searchStudent(final String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }

    public String  makeCodeforcesBadge(final String handle) {
        CodeForcesBadge badge = new CodeForcesBadge(handle, codeforcesClient);
        return badge.getBadge();
    }
}
