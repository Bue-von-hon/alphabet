package uhs.alphabet.board;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import uhs.alphabet.annotation.Timer;
import uhs.alphabet.board.dto.SearchBoardDTO;
import uhs.alphabet.board.spec.BoardSpec;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수
    private int totalPageCount = -1; // 조회 가능한 전체 게시글의 수
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @Timer
    public List<SearchBoardDTO> getBoardList(Integer pageNum) {
        Specification<BoardEntity> visibleSpec = BoardSpec.canVisible();
        Pageable pageable = PageRequest.of(getPage(pageNum), PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<BoardEntity> page = boardRepository.findAll(visibleSpec, pageable);

        int totalPages = page.getTotalPages();
        List<BoardEntity> content = page.getContent();
        if (content.isEmpty()) return Collections.EMPTY_LIST;

        return content.stream()
                .map(entity -> SearchBoardDTO.builder(entity.getBoard_id())
                        .setCreatedTime(entity.getCreatedTime().format(formatter))
                        .setCount(entity.getCount())
                        .setVisible(entity.isVisible())
                        .setWrite(entity.getWriter())
                        .setTitle(entity.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    private static int getPage(Integer pageNum) {
        return pageNum - 1;
    }

    @Transactional
    @Timer
    public Long saveBoard(BoardDto boardDto) {
        if (boardDto.getTitle().equals("")) {
            return -1L;
        }
        if (boardDto.isVisible()) totalPageCount++;
        return boardRepository.save(boardDto.toEntity()).getBoard_id();
    }

    @Transactional
    @Timer
    public Long updateBoard(BoardDto boardDto) {
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
        BoardDto boardDto = BoardDto.convertEntityToDto(boardEntity);

        return boardDto;
    }

    @Transactional
    @Timer
    public void deletePost(Long id, String pw) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        if (!boardEntityOptional.isPresent()) return;
        BoardEntity boardEntity = boardEntityOptional.get();
        if (boardEntity.getPw().toString().equals(pw)) {
            boardRepository.deleteById(id);
            totalPageCount--;
        }
    }

    @Transactional
    public void deletePostAll() {
        totalPageCount=-1;
        boardRepository.deleteAll();
    }

    @Transactional
    @Timer
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findAllByTitle(keyword);

        if (boardEntities.isEmpty()) return Collections.EMPTY_LIST;

        return boardEntities.stream()
                .map(BoardDto::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Timer
    public List<SearchBoardDTO> searchPosts2(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findAllByTitle(keyword);
        if (boardEntities.isEmpty()) return Collections.EMPTY_LIST;

        return boardEntities.stream()
                .map(BoardEntity::getSearchBoardDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Timer
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public List<Integer> getPageList(Integer curPageNum) {
        Specification<BoardEntity> visibleSpec = BoardSpec.canVisible();
        Pageable pageable = PageRequest.of(getPage(curPageNum), PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<BoardEntity> page = boardRepository.findAll(visibleSpec, pageable);

        if (page.isEmpty()) return Collections.EMPTY_LIST;
        int totalPages = page.getTotalPages();
        List<Integer> a = new ArrayList<>();
        if (a.size()!=5 && curPageNum-2 > 0) a.add(curPageNum-2);
        if (a.size()!=5 && curPageNum-1 > 0) a.add(curPageNum-1);
        if (a.size()!=5 && curPageNum > 0) a.add(curPageNum);
        if (a.size()!=5 && curPageNum+1 <= totalPages) a.add(curPageNum+1);
        if (a.size()!=5 && curPageNum+2 <= totalPages) a.add(curPageNum+2);
        if (a.size()!=5 && curPageNum+3 <= totalPages) a.add(curPageNum+3);
        if (a.size()!=5 && curPageNum+4 <= totalPages) a.add(curPageNum+4);
        if (a.size()!=5 && curPageNum-3 > 0) a.add(curPageNum-3);
        if (a.size()!=5 && curPageNum-4 > 0) a.add(curPageNum-4);

        List<Integer> collect = a.stream().sorted().collect(Collectors.toList());
        return collect;
    }

}
