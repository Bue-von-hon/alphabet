package uhs.alphabet.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uhs.alphabet.board.BoardService;
import uhs.alphabet.board.dto.BoardDto;
import uhs.alphabet.config.auth.SecurityConfig;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.service.PersonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = IndexController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@Import({PersonService.class, BoardService.class})
@MockBean(JpaMetamodelMappingContext.class)
class IndexControllerMockTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;
    @MockBean
    private BoardService boardService;


    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String cur = now.format(formatter);

    @Test
    @WithMockUser(roles = "USER")
    void getTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void bobTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bob"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void introductionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/introduction"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void historyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/history"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void contactTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void mirrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mirror"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void journalTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/journal"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/post"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void howtouseTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/howtouse"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void searchTest() throws Exception {
        String arg = "test";
        List<BoardDto> boardDtos = new ArrayList<>();
        BoardDto boardDto = BoardDto.builder()
                .board_id(1L)
                .title("test")
                .content("test")
                .writer("test")
                .visible(true)
                .pw("1234")
                .created_time(cur)
                .count(1)
                .build();
        boardDtos.add(boardDto);
        Mockito.when(boardService.searchPosts(arg)).thenReturn(boardDtos);
        mockMvc.perform(MockMvcRequestBuilders.get("/board/search")
                        .param("keyword", arg)
                        .param("model", "")
                )
                .andExpect(status().isOk());
//                .andExpect(model().attribute("boardList", boardDtos.toString()));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void detailTest() throws Exception {
        Long no = 1L;
        BoardDto boardDto = BoardDto.builder()
                .board_id(1L)
                .title("test")
                .content("test")
                .writer("test")
                .visible(true)
                .pw("1234")
                .created_time(cur)
                .count(1)
                .build();
        Mockito.when(boardService.getBoard(no)).thenReturn(boardDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/board/{no}", no)
                        .param("model", "")
                )
//                .andExpect(model().attribute("board", "asd"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("detail isVisible Test")
    public void detaileTest2() throws Exception {
        Long no = 1L;
        BoardDto boardDto = BoardDto.builder()
                .board_id(no)
                .title("test")
                .content("test")
                .writer("test")
                .visible(false)
                .pw("1234")
                .created_time(cur)
                .count(1)
                .build();
        Mockito.when(boardService.getBoard(no)).thenReturn(boardDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/board/{no}", no)
                        .param("model", "")
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("dodeleteTest")
    public void dodeleteTest() throws Exception {
        Long no = 1L;
        String pw = "1234";

        mockMvc.perform(MockMvcRequestBuilders.get("/dodelete/{no}", no)
                        .param("model", "")
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("doeditTest")
    public void doeditTest() throws Exception {
        Long no = 1L;
        String pw = "1234";

        mockMvc.perform(MockMvcRequestBuilders.get("/doedit/{no}", no)
                        .param("model", "")
                )
                .andExpect(status().isOk());
    }

    //    todo: 수정 시간과 생성 시간 데이터 타입이 다르다
    @Test
    @WithMockUser(roles = "USER")
    public void listTest() throws Exception {
        int pageNum = 1;
        List<BoardDto> boardDtos = new ArrayList<>();
        ArrayList<Integer> pageList = new ArrayList<>();
        now = LocalDateTime.now();
        BoardDto boardDto = BoardDto.builder()
                .board_id(1L)
                .title("test")
                .content("test")
                .writer("test")
                .visible(true)
                .pw("1234")
                .created_time(cur)
                .modified_time(now)
                .count(1)
                .build();
        for (int i = 0; i < 4; i++) {
            boardDtos.add(boardDto);
        }
        pageList.add(1);
//        Mockito.when(boardService.getBoardList(pageNum)).thenReturn(boardDtos);
//        Mockito.when(boardService.getPageList(pageNum)).thenReturn(pageList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
//                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void editTest() throws Exception {
        BoardDto boardDto = BoardDto.builder()
                .board_id(1L)
                .title("test")
                .content("test")
                .writer("test")
                .visible(true)
                .pw("1234")
                .created_time(cur)
                .modified_time(now)
                .count(1)
                .build();
        Long id = 1L;
        Mockito.when(boardService.getBoard(id)).thenReturn(boardDto);
        Long no = 1L;
        String pw = "1234";
        mockMvc.perform(MockMvcRequestBuilders.get("/post/edit/{no}", no)
                        .param("pw", pw)
                )
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/post/edit/{no}", no)
                        .param("pw", "1111")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getSVGTest() throws Exception {
        String stuID = "1234";
        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .handle("test")
                .name("test")
                .rating(1700)
                .stunum(stuID)
                .build();
        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(personDto);
        Mockito.when(personService.searchPerson(stuID)).thenReturn(personDtos);
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/getSVG")
                        .param("stuID", stuID)
                )
                .andExpect(status().isOk());
    }
}