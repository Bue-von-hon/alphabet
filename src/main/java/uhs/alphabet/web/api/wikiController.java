package uhs.alphabet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uhs.alphabet.domain.dto.PageDto;
import uhs.alphabet.domain.service.PageService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class wikiController {
    private final PageService pageService;

    @GetMapping("/home")
    public String home() {
        return "/wiki/home";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        PageDto pageDto = pageService.getPage(id);
        String str = pageDto.getContent();
        model.addAttribute("markdown",str);
        model.addAttribute("page", pageDto);
        return "/wiki/view";
    }
}
