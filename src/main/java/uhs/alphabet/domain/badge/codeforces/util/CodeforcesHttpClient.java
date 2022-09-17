package uhs.alphabet.domain.badge.codeforces.util;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Component
public class CodeforcesHttpClient implements CodeforcesClient {
    private static final String codeforcesUserInfoUrl = "https://codeforces.com/api/user.info?handles=";
    private static final Charset charset = Charset.forName("UTF-8");
    @Override
    public String getData(String handle) {
        String data = "";
        try {
            URL url = new URL(codeforcesUserInfoUrl + handle);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 3초 타임 아웃
            urlConnection.setReadTimeout(3000);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            data = charset.decode(buffer).toString();
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return data;
    }
}
