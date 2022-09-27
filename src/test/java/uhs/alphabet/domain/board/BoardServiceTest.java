package uhs.alphabet.domain.board;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import uhs.alphabet.board.BoardDto;
import uhs.alphabet.board.BoardRepository;
import uhs.alphabet.board.BoardService;
import uhs.alphabet.board.dto.SearchBoardDTO;
import uhs.alphabet.domain.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Import(BoardService.class)
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BoardService boardService;

    @After
    public void cleanup() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
        boardService.deletePostAll();
    }
    @BeforeEach
    public void cleanupEach() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
        boardService.deletePostAll();
    }

    @Test
    public void saveBoardTest() {
        BoardDto boardDto = BoardDto.builder()
                .title("")
                .content("convertTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        Long id = boardService.saveBoard(boardDto);
        BoardDto boardDtoTest = boardService.getBoard(id);
        Assertions.assertEquals("None", boardDtoTest.getTitle());


        boardDto.setTitle("convertTestTitle");
        id = boardService.saveBoard(boardDto);
        boardDtoTest = boardService.getBoard(id);
        Assertions.assertEquals(id, boardDtoTest.getBoard_id());
        Assertions.assertEquals(boardDto.getTitle(), boardDtoTest.getTitle());
        Assertions.assertEquals(boardDto.getContent(), boardDtoTest.getContent());
        Assertions.assertEquals(boardDto.getPw(), boardDtoTest.getPw());
        Assertions.assertEquals(boardDto.getWriter(), boardDtoTest.getWriter());
        Assertions.assertEquals(boardDto.isVisible(), boardDtoTest.isVisible());
        Assertions.assertEquals(boardDto.getIp(), boardDtoTest.getIp());
    }

    @Test
    public void getBoardCountTest() {
        BoardDto boardDto = BoardDto.builder()
                .title("convertTestTitle")
                .content("convertTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        boardService.saveBoard(boardDto);
        Long cnt = boardService.getBoardCount();
        Assertions.assertEquals(1, cnt);
        for (int i = 0; i < 9; i++) boardService.saveBoard(boardDto);
        cnt = boardService.getBoardCount();
        Assertions.assertEquals(10, cnt);
        boardService.deletePostAll();
        cnt = boardService.getBoardCount();
        Assertions.assertEquals(0, cnt);
    }

    @Test
    public void deletePostTest() {
        BoardDto boardDto = BoardDto.builder()
                .title("convertTestTitle")
                .content("convertTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        Long id = boardService.saveBoard(boardDto);
        Long cnt = boardService.getBoardCount();
        Assertions.assertEquals(1, cnt);
        boardService.deletePost(id, "1234");
        cnt = boardService.getBoardCount();
        Assertions.assertEquals(0, cnt);
    }

    @Test
    public void searchPostsTest() {
        List<BoardDto> boardDtos = boardService.searchPosts("searchPostTestTitle");
        Assertions.assertEquals(0, boardDtos.size());
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        Long id = boardService.saveBoard(boardDto);
        boardDtos = boardService.searchPosts("searchPostTestTitle");
        Assertions.assertEquals(1, boardDtos.size());
    }

    @Test
    public void getPageListTest() {
        int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
        int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수

        Integer curPagNum = 1;
        ArrayList<Integer> pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(0, pageList.size());
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        boardService.saveBoard(boardDto);

        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(1, pageList.size());

        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(1, pageList.size());

        for (int i = 0; i < 4; i++) boardService.saveBoard(boardDto);
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(2, pageList.size());

        Long id = 1L;
        for (int i = 0; i < 40; i++) {
            if (i==0) id = boardService.saveBoard(boardDto);
            else boardService.saveBoard(boardDto);
        }
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(BLOCK_PAGE_NUM_COUNT, pageList.size());

        for (int i = 1; i <= 4; i++) {
            Assertions.assertEquals(i, pageList.get(i-1));
        }

        curPagNum = 3;
        pageList = boardService.getPageList(curPagNum);
        for (int i = 1; i <= 4; i++) {
            Assertions.assertEquals(i, pageList.get(i-1));
        }

        curPagNum = 5;
        pageList = boardService.getPageList(curPagNum);
        for (int i = 1; i <= 5; i++) {
            Assertions.assertEquals(i, pageList.get(i-1));
        }
        curPagNum = 6;
        pageList = boardService.getPageList(curPagNum);
        for (int i = 1; i <= 5; i++) {
            Assertions.assertEquals(curPagNum+i-1, pageList.get(i-1));
        }

        // 게시글 N개 지우기 암호 불일치
        for (Long i = id; i < id+40; i++) boardService.deletePost(i, "4321");
        curPagNum = 1;
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(BLOCK_PAGE_NUM_COUNT, pageList.size());

        // 게시글 N개 지우기 암호 일치
        for (Long i = id; i < id+40; i++) boardService.deletePost(i, "1234");
        curPagNum = 1;
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(2, pageList.size());

        boardDto.setVisible(false);
        boardService.saveBoard(boardDto);
        pageList = boardService.getPageList(curPagNum);
        Assertions.assertEquals(2, pageList.size());
        boardDto.setVisible(true);
    }

    @Test
    public void getBoardListTest() {
        int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
        int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수
        List<BoardDto> boardDtos = boardService.getBoardList(1);
        Assertions.assertEquals(0, boardDtos.size());
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        boardService.saveBoard(boardDto);
        boardDtos = boardService.getBoardList(1);
        Assertions.assertEquals(1, boardDtos.size());

        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        boardDtos = boardService.getBoardList(1);
        Assertions.assertEquals(PAGE_POST_COUNT, boardDtos.size());

        // 한 페이지에 있는 4개의 게시글 중 1개가 visible=false인 경우
        boardDto.setVisible(false);
        boardService.saveBoard(boardDto);
        boardDto.setVisible(true);
        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        boardDtos = boardService.getBoardList(2);
        Assertions.assertEquals(PAGE_POST_COUNT-1, boardDtos.size());
    }

    @Test
    public void getBoardListTest2() {
        int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
        int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수
        List<SearchBoardDTO> boardList = boardService.getBoardList2(1);
        Assertions.assertEquals(0, boardList.size());
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        boardService.saveBoard(boardDto);
        boardList = boardService.getBoardList2(1);
        Assertions.assertEquals(1, boardList.size());

        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        boardList = boardService.getBoardList2(1);
        Assertions.assertEquals(PAGE_POST_COUNT, boardList.size());

        // 한 페이지에 있는 4개의 게시글 중 1개가 visible=false인 경우
        boardService.deletePostAll();
        boardDto.setVisible(false);
        boardService.saveBoard(boardDto);
        boardDto.setVisible(true);
        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        boardList = boardService.getBoardList2(1);
        Assertions.assertEquals(PAGE_POST_COUNT-1, boardList.size());
    }
}
