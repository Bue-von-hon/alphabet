package uhs.alphabet.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BoardServiceTest {
    private final BoardService boardService = mock(BoardService.class);

    @Test
    @DisplayName("게시글 저장 테스트")
    void test1() {
        boardService.saveBoard(any());
        verify(boardService, times(1)).saveBoard(any());
    }

    @Test
    @DisplayName("게시글을 10개를 저장하는 테스트")
    void getBoardCountTest() {
        for (int i = 0; i < 10; i++) boardService.saveBoard(any());
        verify(boardService, times(10)).saveBoard(any());
    }

    @Test
    @DisplayName("게시글을 삭제 테스트")
    void deletePostTest() {
        boardService.deletePost(anyLong(), anyString());
        verify(boardService, times(1)).deletePost(anyLong(), anyString());
    }

    @Test
    @DisplayName("게시글 검색 테스트")
    void searchPostsTest() {
        boardService.searchPosts(anyString());
        verify(boardService, times(1)).searchPosts(anyString());
    }
}
