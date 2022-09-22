package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeforcesBadgeMaster;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final CodeforcesBadgeMaster codeforcesBadgeMaster;
    public StudentBadgeUser searchStudent(final String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }

    public String getCodeforcesBadge(final String handle) {
        return codeforcesBadgeMaster.getBadgeByHandle(handle);
    }
}
