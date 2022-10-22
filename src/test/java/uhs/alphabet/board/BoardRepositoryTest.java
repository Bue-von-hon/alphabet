package uhs.alphabet.board;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest
public class BoardRepositoryTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime now = LocalDateTime.now();
    @Autowired
    private BoardRepository boardRepository;

    @After
    public void cleanup() {
        boardRepository.deleteAll();
    }

    @BeforeEach
    public void cleanupEach() {
        boardRepository.deleteAll();
    }

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
        List<BoardEntity> boardEntityWrapper = boardRepository.findByTitleContaining("saveTestTitle");
        Assertions.assertEquals(false, boardEntityWrapper.isEmpty());

        BoardEntity boardEntityTest = boardEntityWrapper.get(0);
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
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining("deleteTest");
        Assertions.assertEquals(false, boardEntities.isEmpty());

        BoardEntity boardEntity = boardEntities.get(0);
        boardRepository.deleteById(boardEntity.getBoard_id());

        List<BoardEntity> deleteTest = boardRepository.findByTitleContaining("deleteTest");
        Assertions.assertEquals(true, deleteTest.isEmpty());
    }

    @Test
    @DisplayName("게시글을 여러개를 저장하고 등록한 게시글 모두를 조회하는 테스트")
    public void searchBoardTest() {
        boardRepository.save(BoardEntity.builder()
                .title("searchTestTitle1")
                .content("deleteTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer1")
                .ip("ip1")
                .build()
        );
        boardRepository.save(BoardEntity.builder()
                .title("searchTestTitle2")
                .content("deleteTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer1")
                .ip("ip1")
                .build()
        );
        boardRepository.save(BoardEntity.builder()
                .title("searchTestTitle3")
                .content("deleteTestContent")
                .pw("1234")
                .visible(true)
                .writer("writer1")
                .ip("ip1")
                .build()
        );
        List<BoardEntity> searchTest = boardRepository.findByTitleContaining("searchTest");
        Assertions.assertEquals(false, searchTest.isEmpty());
        Assertions.assertEquals(3, searchTest.size());
    }
}
