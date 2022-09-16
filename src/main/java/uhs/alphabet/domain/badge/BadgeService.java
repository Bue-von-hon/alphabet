package uhs.alphabet.domain.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public StudentBadgeUser searchStudent(String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }
}
