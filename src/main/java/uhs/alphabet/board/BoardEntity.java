package uhs.alphabet.board;

import lombok.*;
import uhs.alphabet.board.dto.SearchBoardDTO;
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

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public SearchBoardDTO getSearchBoardDTO() {
        LocalDateTime time = getCreatedTime();
        String formatDateTime = time.format(formatter);
        return new SearchBoardDTO.Builder(board_id)
                .setTitle(title)
                .setCount(count)
                .setCreatedTime(formatDateTime)
                .setVisible(visible)
                .setWrite(writer)
                .build();
    }

}
