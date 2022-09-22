package uhs.alphabet.board;

import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long board_id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    @Size(min = 4, max = 6)
    private String pw;
    private int count;
    private String created_time;
    private LocalDateTime modified_time;
    private boolean visible;
    private String ip;
    private String writer;

    public BoardEntity toEntity() {
        BoardEntity boardEntity = BoardEntity.builder()
                .board_id(board_id)
                .title(title)
                .content(content)
                .pw(pw)
                .count(count)
                .writer(writer)
                .ip(ip)
                .visible(visible)
                .build();

        return boardEntity;
    }

    @Builder
    public BoardDto(Long board_id, String title, String content, String pw, int count, String ip, String created_time, LocalDateTime modified_time, boolean visible, String writer) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.pw = pw;
        this.count = count;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.writer = writer;
        this.ip = ip;
        this.visible = visible;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static BoardDto convertEntityToDto(final BoardEntity boardEntity) {
        LocalDateTime time = boardEntity.getCreatedTime();
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
}
