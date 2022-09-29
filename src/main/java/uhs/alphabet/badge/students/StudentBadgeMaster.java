package uhs.alphabet.badge.students;

import uhs.alphabet.domain.entity.PersonEntity;

import java.util.Optional;

public class StudentBadgeMaster {
    public static String getBadgeByEntity(PersonEntity person) {
        StudentMapper mapper = StudentMapper.INSTANCE;
        Optional<Student> user = Optional.ofNullable(mapper.toUser(person));
        if (user.isEmpty()) return StudentBadge.of("None", "None");
        return StudentBadge.of(user.get().getName(), user.get().getHandle());
    }
}
