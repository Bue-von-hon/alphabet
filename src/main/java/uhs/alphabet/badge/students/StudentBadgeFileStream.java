package uhs.alphabet.badge.students;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StudentBadgeFileStream {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String badge;
    static {
        String data = "";
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/stuBadge");
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            data = CHARSET.decode(buffer).toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        badge=data;
    }
    public static String getBadge() {
        return badge;
    }
}
