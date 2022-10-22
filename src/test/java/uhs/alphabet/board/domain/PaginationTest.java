package uhs.alphabet.board.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PaginationTest {

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
    @DisplayName("페이지가 10개일때, 현재 보여지는 페이지 블록이 5개인지 테스트")
    void test4() {
        Pagination pagination = new Pagination(1, 10);
        List<Integer> pageNumbers = pagination.getPageNumbers();
        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(1, pageNumbers.get(0));
        Assertions.assertEquals(2, pageNumbers.get(1));
        Assertions.assertEquals(3, pageNumbers.get(2));
        Assertions.assertEquals(4, pageNumbers.get(3));
        Assertions.assertEquals(5, pageNumbers.get(4));
    }

    @Test
    @DisplayName("총 페이지가 10개이고, 현재 페이지가 3일때, 페이지 블록 = {1, 2, 3, 4, 5}")
    void test5() {
        Pagination pagination = new Pagination(3, 10);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(1, pageNumbers.get(0));
        Assertions.assertEquals(2, pageNumbers.get(1));
        Assertions.assertEquals(3, pageNumbers.get(2));
        Assertions.assertEquals(4, pageNumbers.get(3));
        Assertions.assertEquals(5, pageNumbers.get(4));
    }

    @Test
    @DisplayName("총 페이지가 10개이고, 현재 페이지가 5일때, 페이지 블록 = {3, 4, 5, 6, 7}")
    void test6() {
        Pagination pagination = new Pagination(5, 10);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(3, pageNumbers.get(0));
        Assertions.assertEquals(4, pageNumbers.get(1));
        Assertions.assertEquals(5, pageNumbers.get(2));
        Assertions.assertEquals(6, pageNumbers.get(3));
        Assertions.assertEquals(7, pageNumbers.get(4));
    }

    @Test
    @DisplayName("총 페이지가 10개이고, 현재 페이지가 10일때, 페이지 블록 = {6, 7, 8, 9, 10}")
    void test7() {
        Pagination pagination = new Pagination(10, 10);
        List<Integer> pageNumbers = pagination.getPageNumbers();

        Assertions.assertEquals(5, pageNumbers.size());
        Assertions.assertEquals(6, pageNumbers.get(0));
        Assertions.assertEquals(7, pageNumbers.get(1));
        Assertions.assertEquals(8, pageNumbers.get(2));
        Assertions.assertEquals(9, pageNumbers.get(3));
        Assertions.assertEquals(10, pageNumbers.get(4));
    }
}
