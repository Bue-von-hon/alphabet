package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeForcesBadge;
import uhs.alphabet.badge.codeforces.util.CodeforcesHttpClient;
import uhs.alphabet.badge.codeforces.util.CodeforcesMapper;
import uhs.alphabet.badge.students.StudentBadgeMaster;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.domain.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final PersonRepository personRepository;
    private final CodeforcesHttpClient codeforcesClient;
    private final CodeforcesMapper codeforcesMapper;

    public String getStudentBadgeById(String stuid) {
        PersonEntity personEntity = personRepository.findByStunum(stuid);
        return StudentBadgeMaster.getBadgeByEntity(personEntity);
    }

    public String  makeCodeforcesBadge(final String handle) {
        CodeForcesBadge badge = new CodeForcesBadge(handle, codeforcesMapper, codeforcesClient);
        return badge.getBadge();
    }
}
