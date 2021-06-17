package uhs.alphabet.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uhs.alphabet.annotation.Timer;
import uhs.alphabet.domain.dto.BoardDto;
import uhs.alphabet.domain.entity.BoardEntity;
import uhs.alphabet.domain.repository.BoardRepository;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수

    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        LocalDateTime time = boardEntity.getCreatedTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);
        return BoardDto.builder()
                .board_id(boardEntity.getBoard_id())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .pw(boardEntity.getPw())
                .count(boardEntity.getCount())
                .visible(boardEntity.isVisible())
                .writer(boardEntity.getWriter())
                .created_time(formatDateTime)
                .ip(boardEntity.getIp())
                .modified_time(boardEntity.getModified_time())
                .build();
    }

    @Transactional
    @Timer
    public List<BoardDto> getBoardList(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "visible", "createdTime")));
        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtos = new ArrayList<>();
        if (boardEntities.isEmpty()) return boardDtos;
        for (BoardEntity boardEntity : boardEntities) {
            if (boardEntity.isVisible()) boardDtos.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtos;
    }

    @Transactional
    @Timer
    public Long saveBoard(BoardDto boardDto) {
        if (boardDto.getTitle().equals("")) {
            return -1L;
        }
        return boardRepository.save(boardDto.toEntity()).getBoard_id();
    }

    @Transactional
    @Timer
    public BoardDto getBoard(Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        if (boardEntityWrapper.isEmpty()) {
            BoardDto tmp = BoardDto.builder()
                    .title("None")
                    .content("None")
                    .created_time(LocalDateTime.now().format(formatter))
                    .writer("None")
                    .board_id(-1L)
                    .pw("1234")
                    .build();
            return tmp;
        }
        BoardEntity boardEntity = boardEntityWrapper.get();
        LocalDateTime time = boardEntity.getCreatedTime();
        String formatDateTime = time.format(formatter);
        BoardDto boardDto = this.convertEntityToDto(boardEntity);

        return boardDto;
    }

    @Transactional
    @Timer
    public void deletePost(Long id, String pw) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        if (!boardEntityOptional.isPresent()) return;
        BoardEntity boardEntity = boardEntityOptional.get();
        if (boardEntity.getPw().toString().equals(pw)) boardRepository.deleteById(id);
    }

    @Transactional
    public void deletePostAll() {
        boardRepository.deleteAll();
    }

    @Transactional
    @Timer
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtos = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtos;

        for (BoardEntity boardEntity : boardEntities) {
            boardDtos.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtos;
    }

    @Transactional
    @Timer
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public ArrayList<Integer> getPageList(Integer curPageNum) {
        ArrayList<Integer> pageList = new ArrayList<Integer>();
        // 총 게시글 갯수
        List<BoardEntity> page = boardRepository.findAll();
        int cnt = 0;
        for (BoardEntity tmp : page) {
            if (tmp.isVisible()) cnt++;
        }
        Double postsTotalCount = Double.valueOf(cnt);
//        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            if (idx>5) break;
            pageList.add(val);
        }

        return pageList;
    }

}
