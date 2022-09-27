package uhs.alphabet.domain.board;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import uhs.alphabet.board.BoardDto;
import uhs.alphabet.board.BoardRepository;
import uhs.alphabet.board.BoardService;
import uhs.alphabet.board.dto.SearchBoardDTO;
import uhs.alphabet.domain.repository.PersonRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    private static final BoardDto TEST_BOARDDTO = BoardDto.builder()
            .title("TestTitle")
            .content("TestContent")
            .pw("1234")
            .writer("writer")
            .visible(true)
            .ip("ip")
            .build();

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
    @DisplayName("게시글이 없는 경우 페이지수가 0인지 테스트")
    public void test1() {
        List<Integer> pageList = boardService.getPageList(1);
        Assertions.assertEquals(0, pageList.size());
    }

    @Test
    @DisplayName("현재 페이지가 1이고 게시글이 1개인경우 페이지수가 1인지 테스트")
    public void test2() {
        boardService.saveBoard(TEST_BOARDDTO);
        List<Integer> pageList = boardService.getPageList(1);
        Assertions.assertEquals(1, pageList.size());
    }

    @Test
    @DisplayName("현재 페이지가 1이고 게시글이 4개인경우 페이지가 1인지 테스트")
    public void test4() {
        IntStream.range(0, 4).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(1);
        Assertions.assertEquals(1, pageList.size());
    }

    @Test
    @DisplayName("현재 페이지가 1이고 게시글이 8개인 경우 페이지수가 2인지 테스트")
    public void test5() {
        IntStream.range(0, 8).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(1);
        Assertions.assertEquals(2, pageList.size());
    }

    @Test
    @DisplayName("게시글이 40개일때, 현재 보여지는 페이지 블록이 5개인지 테스트")
    public void test6() {
        IntStream.range(0, 40).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(1);
        Assertions.assertEquals(5, pageList.size());
    }

    @Test
    @DisplayName("게시글이 40개이고, 현재 페이지가 3일때, 페이지 블록 = {1, 2, 3, 4, 5}")
    public void test7() {
        IntStream.range(0, 40).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(3);

        Assertions.assertEquals(5, pageList.size());
        Assertions.assertEquals(1, pageList.get(0));
        Assertions.assertEquals(2, pageList.get(1));
        Assertions.assertEquals(3, pageList.get(2));
        Assertions.assertEquals(4, pageList.get(3));
        Assertions.assertEquals(5, pageList.get(4));
    }

    @Test
    @DisplayName("게시글이 40개이고, 현재 페이지가 5일때, 페이지 블록 = {3, 4, 5, 6, 7}")
    public void test8() {
        IntStream.range(0, 40).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(5);

        Assertions.assertEquals(5, pageList.size());
        Assertions.assertEquals(3, pageList.get(0));
        Assertions.assertEquals(4, pageList.get(1));
        Assertions.assertEquals(5, pageList.get(2));
        Assertions.assertEquals(6, pageList.get(3));
        Assertions.assertEquals(7, pageList.get(4));
    }

    @Test
    @DisplayName("게시글이 40개이고, 현재 페이지가 10일때, 페이지 블록 = {6, 7, 8, 9, 10}")
    public void test9() {
        IntStream.range(0, 40).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(10);

        Assertions.assertEquals(5, pageList.size());
        Assertions.assertEquals(6, pageList.get(0));
        Assertions.assertEquals(7, pageList.get(1));
        Assertions.assertEquals(8, pageList.get(2));
        Assertions.assertEquals(9, pageList.get(3));
        Assertions.assertEquals(10, pageList.get(4));
    }

    @Test
    @DisplayName("게시글이 40개이고, 현재 페이지가 9일때, 페이지 블록 = {6, 7, 8, 9, 10}")
    public void test10() {
        IntStream.range(0, 40).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        List<Integer> pageList = boardService.getPageList(9);

        Assertions.assertEquals(5, pageList.size());
        Assertions.assertEquals(6, pageList.get(0));
        Assertions.assertEquals(7, pageList.get(1));
        Assertions.assertEquals(8, pageList.get(2));
        Assertions.assertEquals(9, pageList.get(3));
        Assertions.assertEquals(10, pageList.get(4));
    }

    @Test
    @DisplayName("게시글 지우기 테스트")
    public void getPageListTest() {
        Integer curPagNum = 1;
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        // 게시글 N개 지우기 암호 불일치
        boardService.deletePostAll();
        IntStream.range(0, 80).forEach(i -> boardService.saveBoard(TEST_BOARDDTO));
        for (Long i = 40L; i < 80L; i++) boardService.deletePost(i, "4321");
        List<Integer> pageList = boardService.getPageList(1);
//        Assertions.assertEquals(5, pageList.size());

        // 게시글 N개 지우기 암호 일치
        for (Long i = 40L; i < 80L; i++) boardService.deletePost(i, "1234");
        pageList = boardService.getPageList(1);
//        Assertions.assertEquals(2, pageList.size());

        boardDto.setVisible(false);
        boardService.saveBoard(boardDto);
        pageList = boardService.getPageList(curPagNum);
//        Assertions.assertEquals(2, pageList.size());
    }

    @Test
    @DisplayName("확인 가능한 게시글 3개와 불가능한 게시글 1개를 저장하는 테스트")
    public void test13() {
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();

        boardService.deletePostAll();
        boardDto.setVisible(false);
        boardService.saveBoard(boardDto);
        boardDto.setVisible(true);
        for (int i = 0; i < 3; i++) boardService.saveBoard(boardDto);
        List<SearchBoardDTO> boardList = boardService.getBoardList(1);
        Assertions.assertEquals(3, boardList.size());
    }

    @Test
    @DisplayName("4개의 게시글을 저장하고 한 페이지에서 보이는지 테스트")
    public void test14() {
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        for (int i = 0; i < 4; i++) boardService.saveBoard(boardDto);
        List<SearchBoardDTO> boardList = boardService.getBoardList(1);
        Assertions.assertEquals(4, boardList.size());
    }

    @Test
    @DisplayName("하나의 게시글을 저장하고 한 페이지에서 보이는지 테스트")
    public void test15() {
        BoardDto boardDto = BoardDto.builder()
                .title("searchPostTestTitle")
                .content("searchPostTestContent")
                .pw("1234")
                .writer("writer")
                .visible(true)
                .ip("ip")
                .build();
        boardService.saveBoard(boardDto);
        List<SearchBoardDTO> boardList = boardService.getBoardList(1);
        Assertions.assertEquals(1, boardList.size());
    }

    @Test
    @DisplayName("게시글이 없을때 한 페이지에 게시글이 없는지 테스트")
    public void test16() {
        List<SearchBoardDTO> boardList = boardService.getBoardList(1);
        Assertions.assertEquals(0, boardList.size());
    }
}
