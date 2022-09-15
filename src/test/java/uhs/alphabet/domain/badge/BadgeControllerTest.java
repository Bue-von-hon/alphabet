package uhs.alphabet.domain.badge;

import org.ehcache.CacheManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import uhs.alphabet.config.auth.SecurityConfig;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = BadgeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class BadgeControllerTest {
    private static final Charset charset = Charset.forName("UTF-8");
    private static List<PersonDto> personDtoList = new ArrayList<>();
    private static String badge;

    @MockBean
    private CacheManager cacheManager;
    @MockBean
    private PersonService personService;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        PersonDto personDto1 = PersonDto.builder()
                .name("liam")
                .handle("jack")
                .id(1L)
                .rating(123)
                .stunum("1234")
                .build();
        personDtoList.add(personDto1);

        ClassPathResource classPathResource = new ClassPathResource("/static/badge/stuBadge");
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            badge = charset.decode(buffer).toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("뱃지 정보 가져오는지 테스트")
    public void test1() throws Exception {
        Mockito.when(personService.searchPerson("1234")).thenReturn(personDtoList);
        mockMvc.perform(
                    get("/stubadge")
                    .param("stuid", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/svg+xml;charset=UTF-8"))
                .andExpect(content().string(
                        badge
                        .replaceAll("\\{(name)}", "liam")
                        .replaceAll("\\{(handle)}", "jack"))
                );
    }
}
