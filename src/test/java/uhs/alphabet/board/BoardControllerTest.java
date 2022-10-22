package uhs.alphabet.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uhs.alphabet.config.auth.SecurityConfig;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = BoardController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@MockBean(JpaMetamodelMappingContext.class)
public class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BoardService boardService;

    @Test
    @WithMockUser("USER")
    @DisplayName("GET /board 요청 테스트")
    public void test1() throws Exception {
        Mockito.when(boardService.getBoardList(anyInt())).thenReturn(Collections.emptyList());
        ArrayList<Integer> pageNumbers = new ArrayList<>();
        pageNumbers.add(1);
        Mockito.when(boardService.getPageList(anyInt())).thenReturn(pageNumbers);
        mockMvc.perform(get("/board"))
                .andExpect(status().isOk());
    }
}
