package uhs.alphabet.badge.codeforces;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CodeforcesBadgeFileStream {
    private static final Charset charset = Charset.forName("UTF-8");
    public String getBadge() {
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/cfBadge");
        ByteBuffer buffer = null;
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            buffer = ByteBuffer.wrap(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        String badge = charset.decode(buffer).toString();
        return badge;
    }
}
