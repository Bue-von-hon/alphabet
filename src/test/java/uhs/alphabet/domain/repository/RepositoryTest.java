package uhs.alphabet.domain.repository;

import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uhs.alphabet.board.BoardEntity;
import uhs.alphabet.board.BoardRepository;
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

    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        BoardEntity entity = boardRepository.save(BoardEntity.builder()
                .title("saveTestTitle")
                .content("saveTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer")
                .ip("ip")
                .build()
        );
        now = LocalDateTime.now();
        List<BoardEntity> boardEntityWrapper = boardRepository.findByTitleContaining("saveTestTitle");
        BoardEntity boardEntityTest = boardEntityWrapper.get(0);

        Assertions.assertEquals(1,boardEntityWrapper.size());
        Assertions.assertEquals(entity.getTitle(), boardEntityTest.getTitle());
        Assertions.assertEquals(entity.getContent(), boardEntityTest.getContent());
        Assertions.assertEquals(entity.getPw(), boardEntityTest.getPw());
        Assertions.assertEquals(entity.getWriter(), boardEntityTest.getWriter());
        Assertions.assertEquals(entity.getIp(), boardEntityTest.getIp());
        Assertions.assertEquals(entity.isVisible(), boardEntityTest.isVisible());
        Assertions.assertEquals(now.format(formatter), boardEntityTest.getCreatedTime().format(formatter));
        Assertions.assertEquals(now.format(formatter), boardEntityTest.getModified_time().format(formatter));
    }

    @Test
    @DisplayName("게시글을 하나 저장하고, 다시 삭제하는 테스트")
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
        now = LocalDateTime.now();
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
