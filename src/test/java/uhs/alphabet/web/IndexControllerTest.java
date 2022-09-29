package uhs.alphabet.web;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uhs.alphabet.board.dto.BoardDto;
import uhs.alphabet.board.BoardService;
import uhs.alphabet.domain.service.PersonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({PersonService.class, BoardService.class})
public class IndexControllerTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String cur = now.format(formatter);
    @Test
    @WithMockUser(roles = "USER")
    public void putPostTest() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                .param("board_id", "1")
                .param("title", "test")
                .param("content", "test")
                .param("writer", "test")
                .param("visible", "true")
                .param("pw", "1234")
                .param("created_time", cur)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/board"));
        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                .param("board_id", "1")
                .param("title", "test")
                .param("content", "test")
                .param("writer", "admin")
                .param("visible", "true")
                .param("pw", "1234")
                .param("created_time", cur)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/board"));
    }
    @Test
    @WithMockUser(roles = "USER")
    public void updateTest() throws Exception {
        Long no = 1L;
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
        now = LocalDateTime.now();
        cur = now.format(formatter);
        Long id = boardService.saveBoard(boardDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/post/edit/{no}", no)
                .param("board_id", "1")
                .param("title", "test2")
                .param("content", "test")
                .param("writer", "admin")
                .param("visible", "true")
                .param("pw", "1234")
                .param("created_time", cur)
        )
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("delete test")
    public void deleteTest() throws Exception {
        Long no = 1L;
        String pw = "1234";
        mockMvc.perform(MockMvcRequestBuilders.delete("/post/{no}", no)
                .param("pw", pw)
        )
                .andExpect(status().is3xxRedirection());
    }
}
