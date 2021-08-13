package uhs.alphabet.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uhs.alphabet.domain.dto.PageDto;
import uhs.alphabet.domain.entity.PageEntity;
import uhs.alphabet.domain.repository.PageRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;

    private PageDto convertEntitytoDto(PageEntity pageEntity) {
        return PageDto.builder()
                .id(pageEntity.getId())
                .content(pageEntity.getContent())
                .title(pageEntity.getTitle())
                .build();
    }

    @Transactional
    public PageDto getPage(Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Optional<PageEntity> pageEntityOptional = pageRepository.findById(id);
        PageDto pageDto;
        if (pageEntityOptional.isEmpty()) {
            String str = "## Marked in the browser" +
                    "\n\nRendered by **marked**." +
                    "\n\nPresented by **KimTaeHun**.";
            pageDto = PageDto.builder()
                    .id(1L)
                    .title("test")
                    .created_time(LocalDateTime.now().format(formatter))
                    .modified_time(LocalDateTime.now().format(formatter))
                    .content(str)
                    .build();
            return pageDto;
        }
        PageEntity pageEntity = pageEntityOptional.get();
        // 필요 없는 부분일지도?
//        LocalDateTime time = pageEntity.getCreatedTime();
//        String formatDateTime = time.format(formatter);
        pageDto = this.convertEntitytoDto(pageEntity);
        return  pageDto;
    }
}
