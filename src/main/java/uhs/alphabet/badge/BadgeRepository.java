package uhs.alphabet.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import uhs.alphabet.badge.students.Student;
import uhs.alphabet.domain.entity.PersonEntity;

public interface BadgeRepository extends JpaRepository<PersonEntity, Long> {
    Student findByStunum(String stunum);
}
