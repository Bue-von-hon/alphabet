package uhs.alphabet.domain.badge;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CfBadge {
    private static final Charset charset = Charset.forName("UTF-8");
    private static final String badge;
    static {
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/cfBadge");
        ByteBuffer buffer = null;
        try (InputStream in = classPathResource.getInputStream()) {
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            buffer = ByteBuffer.wrap(bytes);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        badge = charset.decode(buffer).toString();
    }

    public static String of(final String handle, final String color) {
        return badge.replaceAll("\\{(handle)}", handle).replaceAll("\\{(color)}", color);
    }
}
