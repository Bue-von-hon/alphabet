package uhs.alphabet.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import uhs.alphabet.domain.entity.PersonEntity;

public interface BadgeRepository extends JpaRepository<PersonEntity, Long> {
    StudentBadgeUser findByStunum(String stunum);
}
