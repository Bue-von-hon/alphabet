package uhs.alphabet.domain.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import uhs.alphabet.board.dto.BoardDto;
import uhs.alphabet.board.BoardService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Import(BoardService.class)
public class BoardServiceMockTest {
    @MockBean
    BoardService boardService;

    @Test
    public void saveBoardTest() {
        BoardDto boardDto = BoardDto.builder()
                .title("saveTestTitle")
                .content("saveTestContent")
                .pw("1234")
                .writer("writer")
                .ip("ip")
                .visible(true)
                .build();
        ArgumentCaptor<BoardDto> arg = ArgumentCaptor.forClass(BoardDto.class);
        Mockito.when(boardService.saveBoard(boardDto)).thenReturn(1L);
        boardService.saveBoard(boardDto);

        verify(boardService).saveBoard(arg.capture());
        BoardDto allValues = arg.getValue();
        Assertions.assertNotEquals(allValues.getWriter(), boardDto.getTitle());

        Mockito.verify(boardService, times(1)).saveBoard(boardDto);
        Mockito.verify(boardService).saveBoard(boardDto);
    }
}
