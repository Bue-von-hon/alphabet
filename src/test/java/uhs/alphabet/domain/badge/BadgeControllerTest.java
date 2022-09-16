package uhs.alphabet.domain.badge;

import org.ehcache.Cache;
import org.ehcache.CacheManager;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import uhs.alphabet.config.CacheConfig;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = BadgeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CacheConfig.class)
        }
)
public class BadgeControllerTest {
    private static final Charset charset = Charset.forName("UTF-8");
    private static List<PersonDto> personDtoList = new ArrayList<>();
    private static String stubadge;
    private static String cfbadge;
    private String contentType = "image/svg+xml;charset=UTF-8";

    @Autowired
    private CacheManager cacheManager;
    @MockBean
    private PersonService personService;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @Autowired
    private MockMvc mockMvc;

    private static MockedStatic<CfUser> cfUserMockedStatic;
    @BeforeAll
    public static void setup() {
        cfUserMockedStatic = mockStatic(CfUser.class);
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
            stubadge = charset.decode(buffer).toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        classPathResource = new ClassPathResource("/static/badge/cfBadge");
        ByteBuffer buffer = null;
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            buffer = ByteBuffer.wrap(bytes);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        cfbadge = charset.decode(buffer).toString();
    }

    @AfterAll
    public static void terminate() {
        cfUserMockedStatic.close();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("학생 뱃지 정보 가져오는지 테스트")
    public void test1() throws Exception {
        Mockito.when(personService.searchPerson(anyString())).thenReturn(personDtoList);
        mockMvc.perform(
                    get("/stubadge")
                    .param("stuid", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(
                        stubadge
                        .replaceAll("\\{(name)}", "liam")
                        .replaceAll("\\{(handle)}", "jack"))
                );
    }

    @Test
    @WithMockUser
    @DisplayName("코드포스 뱃지 정보 가져오는 테스트")
    public void test2() throws Exception {
        Mockito.when(CfUser.of(anyString())).thenReturn(new CfUser("jack", "blue"));
        mockMvc.perform(
                get("/cfbadge")
                        .param("handle", "jack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(
                        cfbadge.replaceAll("\\{(handle)}", "jack").replaceAll("\\{(color)}", "blue")
                ));
    }

    @Test
    @WithMockUser
    @DisplayName("코드포스 뱃지 캐싱 테스트")
    public void test3() throws Exception {
        Mockito.when(CfUser.of(anyString())).thenReturn(new CfUser("jack", "blue"));
        mockMvc.perform(
                        get("/cfbadge")
                                .param("handle", "jack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(
                        cfbadge.replaceAll("\\{(handle)}", "jack").replaceAll("\\{(color)}", "blue")
                ));
        Cache<String, CfUser> codeforcesCache = cacheManager.getCache("codeforcesCache", String.class, CfUser.class);
        Assertions.assertEquals(true, codeforcesCache.containsKey("jack"));
    }
}
