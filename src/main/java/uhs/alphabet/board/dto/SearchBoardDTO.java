package uhs.alphabet.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
/*
* DB에서 어플리케이션으로 board 정보를 가져올때 사용하는 DTO
* 게시글 리스트 조회용 DTO
* */
@Getter
public class SearchBoardDTO {
    private final Long boardId;
    @NotBlank
    private final String title;
    private final int count;
    private final String createdTime;
    private final boolean visible;
    private final String writer;

    public SearchBoardDTO(SearchBoardDTOBuilder builder) {
        this.boardId = builder.boardId;
        this.title = builder.title;
        this.visible = builder.visible;
        this.count = builder.count;
        this.createdTime = builder.createdTime;;
        this.writer = builder.writer;
    }

    public static class SearchBoardDTOBuilder {
        private final Long boardId; // 필수값

        private String title;
        private int count;
        private String createdTime;
        private boolean visible;
        private String writer;

        public SearchBoardDTOBuilder(Long boardId) {
            this.boardId = boardId;
        }

        public SearchBoardDTOBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public SearchBoardDTOBuilder setCount(int count) {
            this.count = count;
            return this;
        }

        public SearchBoardDTOBuilder setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public SearchBoardDTOBuilder setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public SearchBoardDTOBuilder setWrite(String writer) {
            this.writer = writer;
            return this;
        }
        public SearchBoardDTO build() {
            return new SearchBoardDTO(this);
        }
    }
}
