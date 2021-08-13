package uhs.alphabet.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uhs.alphabet.domain.entity.PageEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PageDto {
    private Long id;
    private String title;
    private String content;
    private String created_time;
    private String modified_time;

    public PageEntity toEntity() {
        PageEntity pageEntity = PageEntity.builder()
                .id(id)
                .content(content)
                .title(title)
                .build();
        return pageEntity;
    }

    @Builder
    public PageDto(Long id, String title, String content, String created_time, String modified_time) {
        this.id = id;
        this.title=title;
        this.content=content;
        this.created_time=created_time;
        this.modified_time=modified_time;
    }
}
