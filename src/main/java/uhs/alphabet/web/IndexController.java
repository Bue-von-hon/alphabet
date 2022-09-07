package uhs.alphabet.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.reactive.function.client.WebClient;

import uhs.alphabet.annotation.Timer;
import uhs.alphabet.config.auth.LoginUser;
import uhs.alphabet.config.auth.dto.SessionUser;
import uhs.alphabet.domain.badge.StuBadge;
import uhs.alphabet.domain.dto.BoardDto;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.service.BoardService;
import uhs.alphabet.domain.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PersonService personService;
    private final BoardService boardService;
    public String getUserIp() throws Exception {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_Real_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @GetMapping("/")
    @Timer
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) model.addAttribute("userName", user.getName());
        return "index";
    }

    @Timer
    @GetMapping("/bob")
    public String bob() {
        return "bob";
    }

    @Timer
    @GetMapping("/introduction")
    public String introduction() {
        return "introduction";
    }

    @Timer
    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @Timer
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @Timer
    @GetMapping("/mirror")
    public String mirror() {
        return "mirror";
    }

    @Timer
    @GetMapping("/journal")
    public String journal() {
        return "journal";
    }

    @Timer
    @GetMapping("/post")
    public String post() {
        return "post";
    }

    @Timer
    @GetMapping("/howtouse")
    public String howtouse() {
        return "howtouse";
    }

    @PostMapping("/post")
    @Timer
    public String post(@Valid BoardDto boardDto, @LoginUser SessionUser user, Errors errors) throws Exception {
        if (errors.hasErrors()) return "redirect:/board";
        String ip = getUserIp();
        boardDto.setIp(ip);
        boardDto.setVisible(true);
        ArrayList<String> names = new ArrayList<>();
        // 로그인하면 본인 계정 이름으로 writer 설정
//        if (user != null) boardDto.setWriter(user.getName());
        names.add("alphabet");
        names.add("admin");
        names.add("관리자");
        for (String str : names) {
            if (str.equals(boardDto.getWriter())) boardDto.setVisible(false);
        }
        boardService.saveBoard(boardDto);
        return "redirect:/board";
    }

    @DeleteMapping("/post/{no}")
    @Timer
    public String post(@PathVariable("no") Long no, String pw) {
        boardService.deletePost(no, pw);
        return "redirect:/board";
    }

    @GetMapping("/post/edit/{no}")
    @Timer
    public String edit(@PathVariable("no") Long id, Model model, String pw) {
        BoardDto boardDto = boardService.getBoard(id);
        if (!boardDto.getPw().equals(pw)) return "redirect:/board";
        model.addAttribute("boardDto", boardDto);
        return "update";
    }

    @PutMapping("/post/edit/{no}")
    @Timer
    public String update(@Valid BoardDto boardDto, Errors errors) {
        if (errors.hasErrors()) return "redirect:/board";
        boardDto.setVisible(true);
        boardService.updateBoard(boardDto);
        return "redirect:/board";
    }

    @GetMapping("/doedit/{no}")
    public String doEdit(@PathVariable("no") Long id, Model model) {
        model.addAttribute("id", id);
        return "doedit";
    }

    @GetMapping("/dodelete/{no}")
    public String doDelete(@PathVariable("no") Long id, Model model) {
        model.addAttribute("id", id);
        return "dodelete";
    }

    @GetMapping("/testPerson")
    @Timer
    public String test(Model model) {
        PersonDto personDto;
        try {
            personDto = personService.getPerson();
        } catch (Exception e) {
            return e.toString();
        }
        model.addAttribute("person", personDto);
        return "test";
    }

    @GetMapping("/board")
    @Timer
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardList(pageNum);
        ArrayList<Integer> pageList2 = boardService.getPageList(pageNum);
        model.addAttribute("pageList", pageList2);
        model.addAttribute("boardList", boardList);
        return "board";
    }

    @GetMapping("/board/{no}")
    @Timer
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDto = boardService.getBoard(no);
        model.addAttribute("board", boardDto);
        String ip = boardDto.getIp();
        if (!boardDto.isVisible()) {
            boardDto.setTitle("가려진 게시물");
            boardDto.setContent("해당 게시글은 가려졌습니다 문제가 있는 경우 관리자에게 문의하세요");
        }
        return "boardDetail";
    }

    @GetMapping("/board/search")
    @Timer
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardList);
        return "/board";
    }

    @RequestMapping(value = "/api/getSVG", method = RequestMethod.GET, produces = "image/svg+xml", params = "stuID")
    @ResponseBody
    public ResponseEntity<String> getSVG(@RequestParam("stuID") String stuID, Model model) {
        List<PersonDto> personDtos = personService.searchPerson(stuID);
        String handle = "None";
        String name = "None";
        if (!personDtos.isEmpty()) {
            handle = personDtos.get(0).getHandle();
            name = personDtos.get(0).getName();
        }
        return new ResponseEntity<String>(StuBadge.of2(name, handle), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getCF", method = RequestMethod.GET, produces = "image/svg+xml", params = "handle")
    @ResponseBody
    public ResponseEntity<String> getCF(@RequestParam("handle") String handle) {
        String data = "";
        String preSvg = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:svgjs=\"http://svgjs.com/svgjs\" width=\"353\" height=\"134\">\n" +
                "<defs>\n" +
                "    <linearGradient id=\"grad1\" x1=\"0%\" y1=\"0%\" x2=\"30%\" y2=\"0%\">\n" +
                "      <stop offset=\"0%\" style=\"stop-color:rgb(0,0,0);stop-opacity:1\" />\n" +
                "      <stop offset=\"100%\" style=\"stop-color:rgb(255,0,0);stop-opacity:1\" />\n" +
                "    </linearGradient>\n" +
                "  </defs>" +
                "<g>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"15\"></rect>\n" +
                "\t<rect width=\"60\" height=\"40\" fill=\"#ffc519\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"15\"></rect>\n" +
                "\t<rect width=\"30\" height=\"60\" fill=\"#fffa78\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"35\"></rect>\n" +
                "\t<rect width=\"90\" height=\"20\" fill=\"#3cfbff\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"95\"></rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#111111\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"15\"></rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#ff5675\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"55\"></rect>\n" +
                "\t<rect width=\"30\" height=\"20\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"95\"></rect>\n" +
                "\t<rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"15\"></rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"55\"></rect>\n" +
                "\t<rect width=\"60\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"55\"></rect>\n" +
                "\t<rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"95\"></rect>\n" +
                "</g>\n" +
                "<rect id=\"colorR\" width=\"130\" height=\"40\" fill=\"";
        String middleSvg = "\" x=\"200\" y=\"55\" rx=\"10\" ry=\"10\"></rect>\n" +
                "<text id=\"handle\" font-size=\"20\" x=\"210\" y=\"55\" fill=\"white\">\n" +
                "\t<tspan dy=\"26\" x=\"210.0109466053608\">";
        String postSvg = "</tspan>\n" +
                "</text>\n" +
                "<text font-size=\"20\" x=\"160\" y=\"5\">\n" +
                "\t<tspan dy=\"26\" x=\"210.921875\">ALPHABET</tspan>\n" +
                "</text>\n" +
                "<rect width=\"350\" height=\"131\" fill=\"none\" stroke=\"#111111\" rx=\"20\" ry=\"20\" stroke-width=\"3\" x=\"1\" y=\"1\"></rect>\n" +
                "</svg>";
        String color = "blue";
        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("grey", "green", "cyan", "blue", "violet", "orange", "red", "url(#grad1)"));
        String baseUrl = "https://codeforces.com/api/user.info?handles=";
        String url = baseUrl + handle;
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Object trg = client.get().retrieve().bodyToMono(Object.class).block();
        Integer rating = null;
        if (trg != null) {
            data = trg.toString();
            data = data.replaceAll(" ", "");
            StringTokenizer tokens = new StringTokenizer(data, "{}[]=\",");
            ArrayList<String> strArr = new ArrayList<String>();
            while (tokens.hasMoreTokens()) {
                String tmp = tokens.nextToken();
                strArr.add(tmp);
            }
            rating = Integer.parseInt(strArr.get(8));
        } else rating = 300;

        if (rating >= 3000) {
            color = colList.get(7).toString();
        } else if (rating >= 2400) {
            color = colList.get(6).toString();
        } else if (rating >= 2100) {
            color = colList.get(5).toString();
        } else if (rating >= 1900) {
            color = colList.get(4).toString();
        } else if (rating >= 1600) {
            color = colList.get(3).toString();
        } else if (rating >= 1400) {
            color = colList.get(2).toString();
        } else if (rating >= 1200) {
            color = colList.get(1).toString();
        } else {
            color = colList.get(0).toString();
        }

        return new ResponseEntity<String>(preSvg + color + middleSvg + handle + postSvg, HttpStatus.OK);
    }


}
