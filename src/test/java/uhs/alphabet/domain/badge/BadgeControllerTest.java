package uhs.alphabet.domain.badge;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import uhs.alphabet.badge.BadgeController;
import uhs.alphabet.badge.BadgeService;
import uhs.alphabet.badge.StudentBadgeUser;
import uhs.alphabet.config.CacheConfig;
import uhs.alphabet.config.auth.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uhs.alphabet.badge.codeforces.CodeforceBadgeFactory;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.mockito.ArgumentMatchers.anyString;
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
    private static String stubadge;
    private static String cfbadge;
    private String contentType = "image/svg+xml;charset=UTF-8";

    @MockBean
    private CodeforceBadgeFactory codeforceBadgeFactory;
    @MockBean
    private BadgeService badgeService;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/jackLiamstuBadge");
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            stubadge = charset.decode(buffer).toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        classPathResource = new ClassPathResource("/static/badge/jackBlueCfBadge");
        ByteBuffer buffer = null;
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            buffer = ByteBuffer.wrap(bytes);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cfbadge = charset.decode(buffer).toString();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("학생 뱃지 정보 가져오는지 테스트")
    public void test1() throws Exception {
        Mockito.when(badgeService.searchStudent(anyString())).thenReturn(new StudentBadgeUser("liam", "jack"));
        mockMvc.perform(
                        get("/stubadge")
                                .param("stuid", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(stubadge));
    }

    @Test
    @WithMockUser
    @DisplayName("코드포스 뱃지 정보 가져오는 테스트")
    public void test2() throws Exception {
        Mockito.when(badgeService.makeCodeforcesBadge(anyString())).thenReturn(cfbadge);
        mockMvc.perform(
                        get("/cfbadge")
                                .param("handle", "jack"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(cfbadge));
    }

}
