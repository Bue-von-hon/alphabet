package uhs.alphabet.board;

import lombok.*;
import uhs.alphabet.board.dto.searchBoardDTO;
import uhs.alphabet.domain.entity.TimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 4000)
    private String content;

    @Column(length = 20, nullable = false)
    private String pw;

    @Column
    private int count;

    @Column
    private boolean visible;

    @Column
    private String ip;

    @Column
    private String writer;

    @Builder
    public BoardEntity(Long board_id, String title, String content, String pw, int count, String ip, boolean visible, String writer) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.pw = pw;
        this.count = count;
        this.ip = ip;
        this.visible = visible;
        this.writer = writer;
    }

    public searchBoardDTO getSearchBoardDTO() {
        LocalDateTime time = getCreatedTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);
        return new searchBoardDTO(board_id, title, content, pw, count, formatDateTime, getModified_time(), visible, ip, writer);
    }

}
