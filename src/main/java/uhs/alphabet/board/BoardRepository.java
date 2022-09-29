package uhs.alphabet.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import uhs.alphabet.board.dto.SearchBoardDTO;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String title);
    List<BoardEntity> findAllByTitle(String title);
    Page<BoardEntity> findAll(Specification<BoardEntity> spec, Pageable pageable);
}
