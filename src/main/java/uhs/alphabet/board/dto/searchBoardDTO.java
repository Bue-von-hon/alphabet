package uhs.alphabet.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/*
* DB에서 어플리케이션으로 board 정보를 가져올때 사용하는 DTO
* */
@Getter
public class searchBoardDTO {
    private final Long board_id;
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;
    @NotBlank
    @Size(min = 4, max = 6)
    private final String pw;
    private final int count;
    private final String created_time;
    private final LocalDateTime modified_time;
    private final boolean visible;
    private final String ip;
    private final String writer;

    public searchBoardDTO(Long board_id, String title, String content, String pw, int count, String created_time, LocalDateTime modified_time, boolean visible, String ip, String writer) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.pw = pw;
        this.count = count;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.visible = visible;
        this.ip = ip;
        this.writer = writer;
    }
}
