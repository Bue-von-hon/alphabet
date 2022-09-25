package uhs.alphabet.domain.repository;

import uhs.alphabet.domain.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>{
    List<PersonEntity> findByStunumContaining(String stunum);
    PersonEntity findByStunum(String stunum);
}
