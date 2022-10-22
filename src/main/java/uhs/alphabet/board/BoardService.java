package uhs.alphabet.board;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhs.alphabet.annotation.Timer;
import uhs.alphabet.board.domain.Pagination;
import uhs.alphabet.board.dto.BoardDto;
import uhs.alphabet.board.dto.SearchBoardDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uhs.alphabet.board.spec.BoardSpec.canVisible;

@RequiredArgsConstructor
@Service
public class BoardService {

    private static final int BLOCK_PAGE_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수
    private final BoardRepository boardRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static int getPage(Integer pageNum) {
        return pageNum - 1;
    }

    @Transactional
    @Timer
    public List<SearchBoardDTO> getBoardList(Integer pageNum) {
        Specification<BoardEntity> visibleSpec = canVisible();
        Pageable pageable = PageRequest.of(getPage(pageNum), PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<BoardEntity> page = boardRepository.findAll(visibleSpec, pageable);

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
            return BoardDto.builder()
                    .title("None")
                    .content("None")
                    .created_time(LocalDateTime.now().format(formatter))
                    .writer("None")
                    .board_id(-1L)
                    .pw("1234")
                    .build();
        }
        BoardEntity boardEntity = boardEntityWrapper.get();
        return BoardDto.convertEntityToDto(boardEntity);
    }

    @Transactional
    @Timer
    public void deletePost(Long id, String pw) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        if (!boardEntityOptional.isPresent()) return;
        BoardEntity boardEntity = boardEntityOptional.get();
        if (boardEntity.getPw().toString().equals(pw)) {
            boardRepository.deleteById(id);
        }
    }

    @Transactional
    public void deletePostAll() {
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
        Specification<BoardEntity> visibleSpec = canVisible();
        Pageable pageable = PageRequest.of(getPage(curPageNum), PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<BoardEntity> page = boardRepository.findAll(visibleSpec, pageable);

        Pagination pagenation = new Pagination(curPageNum, page.getTotalPages());
        return pagenation.getPageNumbers();
    }
}
