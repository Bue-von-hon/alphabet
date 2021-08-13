package uhs.alphabet.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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


}
