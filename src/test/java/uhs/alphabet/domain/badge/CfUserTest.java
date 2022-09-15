package uhs.alphabet.domain.badge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CfUserTest {
    @Test
    @DisplayName("외부api를 통해 가져온 정보를 올바르세 파싱하는지 테스트")
    public void test1() {
        try {
            String data = "{\"status\":\"OK\",\"result\":[{\"country\":\"South Korea\",\"lastOnlineTimeSeconds\":1662843288,\"rating\":1005,\"friendOfCount\":1,\"titlePhoto\":\"//userpic.codeforces.org/no-title.jpg\",\"handle\":\"BueVonHun\",\"avatar\":\"//userpic.codeforces.org/no-avatar.jpg\",\"contribution\":0,\"organization\":\"hyupsunguniversity\",\"rank\":\"newbie\",\"maxRating\":1189,\"registrationTimeSeconds\":1590993904,\"maxRank\":\"newbie\"}]}";
            HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
            Charset charset = Charset.forName("UTF-8");
            byte[] testBytes = data.getBytes(charset);
            InputStream in = new ByteArrayInputStream(testBytes);
            Mockito.when(mockConnection.getInputStream()).thenReturn(in);

            InputStream inputStream = mockConnection.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            String s = charset.decode(buffer).toString();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> stringObjectMap = mapper.readValue(s, new TypeReference<Map<String, Object>>() {});
            ArrayList<HashMap<String, Object>> result = (ArrayList<HashMap<String, Object>>) stringObjectMap.get("result");
            HashMap<String, Object> map= result.get(0);
            String handle = (String) map.get("handle");
            String rank = (String) map.get("rank");
            Assertions.assertNotNull(handle);
            Assertions.assertNotNull(rank);
            Assertions.assertEquals("BueVonHun", handle);
            Assertions.assertEquals("newbie", rank);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
