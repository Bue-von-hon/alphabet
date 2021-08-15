package uhs.alphabet.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uhs.alphabet.domain.dto.PageDto;
import uhs.alphabet.domain.service.PageService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wiki")
public class wikiController {
    private final PageService pageService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "wiki/main";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        PageDto pageDto = pageService.getPage(id);
        String str = pageDto.getContent();
        model.addAttribute("markdown",str);
        model.addAttribute("page", pageDto);
        return "wiki/pageDetail";
    }
}
