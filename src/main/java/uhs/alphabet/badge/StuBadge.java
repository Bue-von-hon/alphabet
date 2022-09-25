package uhs.alphabet.badge;

import org.springframework.core.io.ClassPathResource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class StuBadge {
    private static final Charset charset = Charset.forName("UTF-8");
    private static final String badge;
    static {
        String data = "";
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/stuBadge");
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            data = charset.decode(buffer).toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        badge=data;
    }
    public static String of(final String name, final String handle) {
        return badge.replaceAll("\\{(name)}", name).replaceAll("\\{(handle)}", handle);
    }
}
