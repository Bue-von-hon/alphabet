package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeForcesBadge;
import uhs.alphabet.badge.codeforces.util.CodeforcesHttpClient;
import uhs.alphabet.badge.codeforces.util.CodeforcesMapper;
import uhs.alphabet.badge.students.Student;
import uhs.alphabet.badge.students.StudentBadge;
import uhs.alphabet.badge.students.StudentMapper;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.domain.repository.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final PersonRepository personRepository;
    private final CodeforcesHttpClient codeforcesClient;
    private final CodeforcesMapper codeforcesMapper;

    public String getStudentBadgeByHandle(String stuid) {
        PersonEntity personEntity = personRepository.findByStunum(stuid);
        StudentMapper mapper = StudentMapper.INSTANCE;
        Optional<Student> user = Optional.ofNullable(mapper.toUser(personEntity));
        if (user.isEmpty()) return StudentBadge.of("None", "None");
        return StudentBadge.of(user.get().getName(), user.get().getHandle());
    }

    public String  makeCodeforcesBadge(final String handle) {
        CodeForcesBadge badge = new CodeForcesBadge(handle, codeforcesMapper, codeforcesClient);
        return badge.getBadge();
    }
}
