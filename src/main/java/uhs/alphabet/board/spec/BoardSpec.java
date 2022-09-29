package uhs.alphabet.board.spec;

import org.springframework.data.jpa.domain.Specification;
import uhs.alphabet.board.BoardEntity;
import uhs.alphabet.board.dto.SearchBoardDTO;

public class BoardSpec {
    public static Specification<BoardEntity> canVisible() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visible"), true);
    }
}
