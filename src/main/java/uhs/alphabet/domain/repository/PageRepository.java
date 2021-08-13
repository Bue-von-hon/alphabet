package uhs.alphabet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uhs.alphabet.domain.entity.PageEntity;

public interface PageRepository extends JpaRepository<PageEntity, Long> {
}
