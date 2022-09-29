package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeforcesBadgeMaster;
import uhs.alphabet.badge.students.StudentBadgeMaster;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.domain.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final PersonRepository personRepository;
    public String getStudentBadgeById(String stuid) {
        PersonEntity personEntity = personRepository.findByStunum(stuid);
        return StudentBadgeMaster.getBadgeByEntity(personEntity);
    }

    public String  makeCodeforcesBadge(final String handle) {
        CodeforcesBadgeMaster codeforcesBadgeMaster = new CodeforcesBadgeMaster();
        return codeforcesBadgeMaster.getBadgeByHandle(handle);
    }
}
