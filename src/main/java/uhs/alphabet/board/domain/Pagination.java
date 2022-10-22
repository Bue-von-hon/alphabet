package uhs.alphabet.board.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {
    private static final int BLOCK_PAGE_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final List<Integer> EMPTY_PAGE_NUMBERS = Collections.EMPTY_LIST;
    private final int curPageNumber;
    private final int totalPages;

    public Pagination(int curPageNumber, int totalPages) {
        this.curPageNumber = curPageNumber;
        this.totalPages = totalPages;
    }

    private boolean isOverThanBlockNumber(int totalPages) {
        return totalPages > BLOCK_PAGE_COUNT;
    }

    private boolean isZero(int totalPages) {
        return totalPages == 0;
    }

    public List<Integer> getPageNumbers() {
        if (isZero(totalPages)) return EMPTY_PAGE_NUMBERS;

        List<Integer> ret = new ArrayList<>();
        if (!isOverThanBlockNumber(totalPages)) {
            IntStream.range(1, totalPages + 1).forEach(ret::add);
            return ret;
        }
        ret.add(curPageNumber);
        int l = curPageNumber;
        int r = curPageNumber;
        while (ret.size() != BLOCK_PAGE_COUNT) {
            if (r + 1 <= totalPages) ret.add(r + 1);
            if (l - 1 > 0) ret.add(l - 1);
            r++;
            l--;
        }
        return ret.stream().sorted().collect(Collectors.toList());
    }
}
