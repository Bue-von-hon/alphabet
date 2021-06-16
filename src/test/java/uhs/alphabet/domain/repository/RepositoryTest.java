package uhs.alphabet.domain.repository;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uhs.alphabet.domain.entity.BoardEntity;
import uhs.alphabet.domain.entity.PersonEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PersonRepository personRepository;

    @After
    public void cleanup() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
    }
    @BeforeEach
    public void cleanupEach() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
    }

    /*
    * saveBoard을 테스트합니다
    */
    @Test
    @DisplayName("saveBoard test 한번 저장")
    public void saveBoard() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        BoardEntity boardEntity = BoardEntity.builder()
                .title("saveTestTitle")
                .content("saveTestContent")
                .pw("1234")
                .writer("writer")
                .ip("ip")
                .visible(true)
                .build();
        boardRepository.save(BoardEntity.builder()
                .title("saveTestTitle")
                .content("saveTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer")
                .ip("ip")
                .build()
        );

        List<BoardEntity> boardEntityWrapper = boardRepository.findByTitleContaining("saveTestTitle");
        BoardEntity boardEntityTest = boardEntityWrapper.get(0);

        Assertions.assertEquals(1,boardEntityWrapper.size());
        Assertions.assertEquals(boardEntity.getTitle(), boardEntityTest.getTitle());
        Assertions.assertEquals(boardEntity.getContent(), boardEntityTest.getContent());
        Assertions.assertEquals(boardEntity.getPw(), boardEntityTest.getPw());
        Assertions.assertEquals(boardEntity.getWriter(), boardEntityTest.getWriter());
        Assertions.assertEquals(boardEntity.getIp(), boardEntityTest.getIp());
        Assertions.assertEquals(boardEntity.isVisible(), boardEntityTest.isVisible());
        Assertions.assertEquals(now.format(formatter), boardEntityTest.getCreatedTime().format(formatter));
        Assertions.assertEquals(now.format(formatter), boardEntityTest.getModified_time().format(formatter));
    }

    @Test
    public void deleteBoard() {
        boardRepository.save(BoardEntity.builder()
                .title("deleteTestTitle")
                .content("deleteTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer1")
                .ip("ip1")
                .build()
        );
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining("deleteTest");
        int sz = boardEntities.size();
        Assertions.assertEquals(sz, 1);

        BoardEntity boardEntity = boardEntities.get(0);
        boardRepository.deleteById(boardEntity.getBoard_id());

        boardEntities = boardRepository.findByTitleContaining("deleteTest");
        sz = boardEntities.size();
        Assertions.assertEquals(sz, 0);
    }

    @Test
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
        List<PersonEntity> personEntityWrapper = personRepository.findByStunumContaining("savePerson1234");
        PersonEntity personEntityTest = personEntityWrapper.get(0);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Assertions.assertEquals(personEntity.getName(), personEntityTest.getName());
        Assertions.assertEquals(personEntity.getHandle(), personEntityTest.getHandle());
        Assertions.assertEquals(personEntity.getStunum(), personEntityTest.getStunum());
        Assertions.assertEquals(personEntity.getRating(), personEntityTest.getRating());
        Assertions.assertEquals(now.format(formatter), personEntityTest.getCreatedTime().format(formatter));
        Assertions.assertEquals(now.format(formatter), personEntityTest.getModified_time().format(formatter));
    }

    @Test
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
