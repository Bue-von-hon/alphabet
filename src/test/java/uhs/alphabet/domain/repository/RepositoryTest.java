package uhs.alphabet.domain.repository;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uhs.alphabet.domain.entity.PersonEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @After
    public void cleanup() {
        personRepository.deleteAll();
    }
    @BeforeEach
    public void cleanupEach() {
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("회원을 등록하는 테스트")
    public void savePerson() {
        PersonEntity personEntity = PersonEntity.builder()
                .handle("savePersonHandle")
                .stunum("savePerson1234")
                .id(1L)
                .name("name")
                .rating(1700)
                .build();
        personRepository.save(PersonEntity.builder()
                .handle("savePersonHandle")
                .stunum("savePerson1234")
                .rating(1700)
                .name("name")
                .build()
        );
        now = LocalDateTime.now();
        List<PersonEntity> personEntityWrapper = personRepository.findByStunumContaining("savePerson1234");
        PersonEntity personEntityTest = personEntityWrapper.get(0);

        Assertions.assertEquals(personEntity.getName(), personEntityTest.getName());
        Assertions.assertEquals(personEntity.getHandle(), personEntityTest.getHandle());
        Assertions.assertEquals(personEntity.getStunum(), personEntityTest.getStunum());
        Assertions.assertEquals(personEntity.getRating(), personEntityTest.getRating());
        Assertions.assertEquals(now.format(formatter), personEntityTest.getCreatedTime().format(formatter));
        Assertions.assertEquals(now.format(formatter), personEntityTest.getModified_time().format(formatter));
    }

    @Test
    @DisplayName("회원을 등록하고 삭제하는 테스트")
    public void deletePerson() {
        personRepository.save(PersonEntity.builder()
                .handle("handle")
                .stunum("1234")
                .rating(1700)
                .name("name")
                .build()
        );
        List<PersonEntity> personEntities = personRepository.findByStunumContaining("1234");
        int sz = personEntities.size();
        Assertions.assertEquals(sz, 1);
        PersonEntity personEntity = personEntities.get(0);

        personRepository.deleteById(personEntity.getId());

        personEntities = personRepository.findByStunumContaining("1234");
        sz = personEntities.size();
        Assertions.assertEquals(sz, 0);
    }
}
