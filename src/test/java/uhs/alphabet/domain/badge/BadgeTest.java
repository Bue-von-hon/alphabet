package uhs.alphabet.domain.badge;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class BadgeTest {

    @Test
    @DisplayName("파일 읽어오기 테스트")
    public void test1() throws IOException, URISyntaxException {
        Path stuBadge = Path.of(ClassLoader.getSystemResource("static/badge/stuBadge").toURI());
        byte[] bytes = Files.readAllBytes(stuBadge);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        Charset charset = Charset.forName("UTF-8");
        String badge = charset.decode(buffer).toString();
        badge = badge.replaceAll("\\{(name)}", "abc");
        badge = badge.replaceAll("\\{(handle)}", "cba");
        System.out.println(badge);
    }
}
