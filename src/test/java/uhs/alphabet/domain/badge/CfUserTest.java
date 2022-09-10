package uhs.alphabet.domain.badge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CfUserTest {
    @Test
    @DisplayName("")
    public void test1() {
        try {
            String query = "buevonhun";
            URL url = new URL("https://codeforces.com/api/user.info?handles="+query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 3초 타임 아웃
            urlConnection.setReadTimeout(3000);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            Charset charset = Charset.forName("UTF-8");
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
