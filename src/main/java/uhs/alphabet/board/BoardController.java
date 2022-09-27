package uhs.alphabet.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uhs.alphabet.annotation.Timer;
import uhs.alphabet.board.dto.SearchBoardDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board")
    @Timer
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<SearchBoardDTO> boardList = boardService.getBoardList(pageNum);
        ArrayList<Integer> pageList = boardService.getPageList(pageNum);
        model.addAttribute("pageList", pageList);
        model.addAttribute("boardList", boardList);
        return "board";
    }
}
