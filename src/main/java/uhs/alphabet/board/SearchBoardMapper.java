package uhs.alphabet.board;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import uhs.alphabet.board.dto.SearchBoardDTO;

@Mapper
public interface SearchBoardMapper {
    SearchBoardMapper INSTANCE = Mappers.getMapper(SearchBoardMapper.class);
    SearchBoardDTO toSearchBoardDTO(BoardEntity entity);
}
