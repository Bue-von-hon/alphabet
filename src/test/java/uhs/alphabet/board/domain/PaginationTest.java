package uhs.alphabet.board.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class PaginationTest {

    private static Stream<Arguments> argumentsStream1() {
        return Stream.of(
                Arguments.of("총 페이지가 10이고, 현재 페이지가 9 = {6, 7, 8, 9, 10}", 9, 10),
                Arguments.of("총 페이지가 10이고, 현재 페이지가 10 = {6, 7, 8, 9, 10}", 10, 10)
        );
    }

    private static Stream<Arguments> argumentsStream2() {
        return Stream.of(
                Arguments.of("총 페이지가 10이고, 현재 페이지가 1 = {1, 2, 3, 4, 5}", 1, 10),
                Arguments.of("총 페이지가 10이고, 현재 페이지가 3 = {1, 2, 3, 4, 5}", 3, 10)
        );
    }

    @Test
    @DisplayName("게시글이 없는 경우 페이지수가 0인지 테스트")
    void test1() {
        Pagination pagination = new Pagination(1, 0);
        List<Integer> pageNumbers = pagination.getPageNumbers();
        Assertions.assertEquals(0, pageNumbers.size());
    }

    @Test
    @DisplayName("현재 페이지가 1이고 총 페이지가 하나인 경우 페이지수가 1인지 테스트")
    void test2() {
        Pagination pagination = new Pagination(1, 1);
        List<Integer> pageNumbers = pagination.getPageNumbers();
        Assertions.assertEquals(1, pageNumbers.size());
        Assertions.assertEquals(1, pageNumbers.get(0));
    }

    @Test
    @DisplayName("현재 페이지가 1이고 총 페이지가 2개인 경우 페이지수가 2인지 테스트")
    void test3() {
        Pagination pagination = new Pagination(1, 2);
        List<Integer> pageNumbers = pagination.getPageNumbers();
        Assertions.assertEquals(2, pageNumbers.size());
        Assertions.assertEquals(1, pageNumbers.get(0));
        Assertions.assertEquals(2, pageNumbers.get(1));
    }

    @Test
    @DisplayName("총 페이지가 10개이고, 현재 페이지가 5일때, 페이지 블록 = {3, 4, 5, 6, 7}")
    void test4() {
        Pagination pagination = new Pagination(5, 10);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(3, pageNumbers.get(0));
        Assertions.assertEquals(4, pageNumbers.get(1));
        Assertions.assertEquals(5, pageNumbers.get(2));
        Assertions.assertEquals(6, pageNumbers.get(3));
        Assertions.assertEquals(7, pageNumbers.get(4));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("argumentsStream2")
    void test5(String message, int curPageNumber, int totalPages) {
        Pagination pagination = new Pagination(curPageNumber, totalPages);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(1, pageNumbers.get(0));
        Assertions.assertEquals(2, pageNumbers.get(1));
        Assertions.assertEquals(3, pageNumbers.get(2));
        Assertions.assertEquals(4, pageNumbers.get(3));
        Assertions.assertEquals(5, pageNumbers.get(4));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("argumentsStream1")
    void test6(String message, int curPageNumber, int totalPages) {
        Pagination pagination = new Pagination(curPageNumber, totalPages);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(6, pageNumbers.get(0));
        Assertions.assertEquals(7, pageNumbers.get(1));
        Assertions.assertEquals(8, pageNumbers.get(2));
        Assertions.assertEquals(9, pageNumbers.get(3));
        Assertions.assertEquals(10, pageNumbers.get(4));
    }
}
