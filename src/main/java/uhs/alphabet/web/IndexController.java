package uhs.alphabet.web;

import lombok.RequiredArgsConstructor;
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

        return new ResponseEntity<String>("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:svgjs=\"http://svgjs.com/svgjs\" width=\"353\" height=\"134\">\n" +
                "  <style>\n" +
                "    .pen {\n" +
                "      fill: none;\n" +
                "      stroke: url(#rainbow);\n" +
                "      stroke-width: 2;\n" +
                "      stroke-linecap: round;\n" +
                "    }\n" +
                "    .p{\n" +
                "      stroke-dasharray: 140 140;\n" +
                "      stroke-dashoffset: 140;\n" +
                "      animation-duration: 1s;\n" +
                "      animation-delay: 4s;\n" +
                "      animation-name: draw;\n" +
                "      animation-direction: alternate;\n" +
                "      animation-timing-function: linear;\n" +
                "      animation-fill-mode: forwards;\n" +
                "    }\n" +
                "    .b{\n" +
                "      stroke-dasharray: 280 280;\n" +
                "      stroke-dashoffset: 280;\n" +
                "      animation-duration: 2s;\n" +
                "      animation-delay: 5.5s;\n" +
                "      animation-name: draw;\n" +
                "      animation-direction: alternate;\n" +
                "      animation-timing-function: linear;\n" +
                "      animation-fill-mode: forwards;\n" +
                "    }\n" +
                "    @keyframes draw {\n" +
                "      to {\n" +
                "        stroke-dashoffset: 0;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "  <defs>\n" +
                "    <linearGradient id=\"rainbow\" x1=\"60%\" x2=\"85%\" y1=\"0\" y2=\"0\" gradientUnits=\"userSpaceOnUse\" >\n" +
                "      <stop stop-color=\"#FF0000\" offset=\"0%\"/>\n" +
                "      <stop stop-color=\"#FF9900\" offset=\"33%\"/>\n" +
                "      <stop stop-color=\"#CC33FF\" offset=\"66%\"/>\n" +
                "      <stop stop-color=\"#ff54dd\" offset=\"100%\"/> \n" +
                "    </linearGradient>\n" +
                "  </defs>\n" +
                "  <rect width=\"350\" height=\"131\" fill=\"white\" stroke=\"#111111\" rx=\"20\" ry=\"20\" stroke-width=\"3\" x=\"1\" y=\"1\"></rect>\n" +
                "<g>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"15\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation1\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"60\" height=\"40\" fill=\"#ffc519\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"15\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation2\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation1.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"0;60\" dur=\"0.4s\" begin=\"animation1.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"0;40\" dur=\"0.4s\" begin=\"animation1.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"60\" fill=\"#fffa78\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"35\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation3\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation2.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation2.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;60\" dur=\"0.4s\" begin=\"animation2.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"90\" height=\"20\" fill=\"#3cfbff\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"95\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation4\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation3.end\"/>\n" +
                "    <animate attributeName=\"x\" values=\"0;10\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
                "    <animate attributeName=\"y\" values=\"0;95\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;90\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;20\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#111111\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"15\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation5\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation4.end\"/>\n" +
                "    <animate attributeName=\"x\" values=\"0;70\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
                "    <animate attributeName=\"y\" values=\"0;15\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"#ff5675\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"55\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation6\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4\" fill=\"freeze\" begin=\"animation5.end\"/>\n" +
                "    <animate attributeName=\"x\" values=\"0;130\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
                "    <animate attributeName=\"y\" values=\"0;55\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"20\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"95\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation7\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation6.end\"/>\n" +
                "    <animate attributeName=\"x\" values=\"0;100\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
                "    <animate attributeName=\"y\" values=\"0;95\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
                "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
                "    <animate attributeName=\"height\" values=\"100;20\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
                "\t</rect>\n" +
                "<!-- \n" +
                "    blank\n" +
                "-->\n" +
                "  <rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"15\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation8\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"55\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation9\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"60\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"55\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation10\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
                "\t</rect>\n" +
                "\t<rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"95\" opacity=\"0\">\n" +
                "\t  <animate id=\"animation11\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
                "\t</rect>\n" +
                "</g>\n" +
                "  <!--A-->\n" +
                "  <line x1=\"217\" y1=\"21\" x2=\"217\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line1\" attributeName=\"x2\" values=\"217 ; 210\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
                "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"217\" y1=\"21\" x2=\"217\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line2\" attributeName=\"x2\" values=\"217 ; 224\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
                "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"212\" y1=\"32\" x2=\"212\" y2=\"32\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line3\" attributeName=\"x2\" values=\"212 ; 221\" dur=\"0.2s\" fill=\"freeze\" begin=\"line2.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line2.end\"/>\n" +
                "  </line>\n" +
                "  <!--L-->\n" +
                "  <line x1=\"227\" y1=\"21\" x2=\"227\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line4\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line3.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line3.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"227\" y1=\"37\" x2=\"227\" y2=\"37\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line5\" attributeName=\"x2\" values=\"227 ; 234\" dur=\"0.2s\" fill=\"freeze\" begin=\"line4.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line4.end\"/>\n" +
                "  </line>\n" +
                "  <!--P-->\n" +
                "  <line x1=\"237\" y1=\"21\" x2=\"237\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line6\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
                "  </line>\n" +
                "  <path d=\"M237 21 C247 21 247 30 237 30\" class=\"pen p\"></path>\n" +
                "  <!--H-->\n" +
                "  <line x1=\"247\" y1=\"21\" x2=\"247\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line8\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line6.end+0.2\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line6.end+0.2\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"247\" y1=\"30\" x2=\"247\" y2=\"30\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line9\" attributeName=\"x2\" values=\"247 ; 256\" dur=\"0.2s\" fill=\"freeze\" begin=\"line8.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line8.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"256\" y1=\"21\" x2=\"256\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line10\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line9.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line9.end\"/>\n" +
                "  </line>\n" +
                "  <!--A-->\n" +
                "  <line x1=\"267\" y1=\"21\" x2=\"267\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line11\" attributeName=\"x2\" values=\"267 ; 260\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
                "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"267\" y1=\"21\" x2=\"267\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line12\" attributeName=\"x2\" values=\"267 ; 274\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
                "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"262\" y1=\"32\" x2=\"262\" y2=\"32\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line13\" attributeName=\"x2\" values=\"262 ; 271\" dur=\"0.2s\" fill=\"freeze\" begin=\"line12.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line12.end\"/>\n" +
                "  </line>\n" +
                "  <!--B-->\n" +
                "  <line x1=\"277\" y1=\"21\" x2=\"277\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line14\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line13.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line13.end\"/>\n" +
                "  </line>\n" +
                "  <path d=\"M277 21 C287 21 287 29 277 29 287 29 287 37 277 37\" class=\"pen b\"></path>\n" +
                "  <!--E-->\n" +
                "  <line x1=\"287\" y1=\"21\" x2=\"287\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line16\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line14.end+0.2\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line14.end+0.2\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"287\" y1=\"21\" x2=\"287\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line17\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line16.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line16.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"287\" y1=\"37\" x2=\"287\" y2=\"37\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line18\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line17.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line17.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"287\" y1=\"29\" x2=\"287\" y2=\"29\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line19\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line18.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line18.end\"/>\n" +
                "  </line>\n" +
                "  <!--T-->\n" +
                "  <line x1=\"297\" y1=\"21\" x2=\"297\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line20\" attributeName=\"x2\" values=\"297 ; 306\" dur=\"0.2s\" fill=\"freeze\" begin=\"line19.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line19.end\"/>\n" +
                "  </line>\n" +
                "  <line x1=\"302\" y1=\"21\" x2=\"302\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
                "    <animate id=\"line21\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line20.end\"/>\n" +
                "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line20.end\"/>\n" +
                "  </line>\n" +
                "<!-- \n" +
                "    text \n" +
                "-->\n" +
                "  <text font-size=\"20\" x=\"210\" y=\"40\" opacity=\"0\">\n" +
                "\t  <tspan id=\"name\" dy=\"26\" x=\"210.0109466053608\">" + name + "</tspan>\n" +
                "    <animate id=\"animation14\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"line21.end\"/>\n" +
                "  </text>\n" +
                "  <text font-size=\"20\" x=\"210\" y=\"60\" opacity=\"0\">\n" +
                "\t  <tspan id=\"handle\" dy=\"26\" x=\"210.0109466053608\">" + handle + "</tspan>\n" +
                "    <animate id=\"animation13\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"line21.end\"/>\n" +
                "  </text>\n" +
                "</svg>", HttpStatus.OK);
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
