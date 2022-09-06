package uhs.alphabet.domain.badge;

import org.springframework.core.io.ClassPathResource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StuBadge {
    private static final Charset charset = Charset.forName("UTF-8");
    public static String of(final String name, final String handle) {
       String badge = "";
       ClassPathResource classPathResource = new ClassPathResource("/static/badge/stuBadge");
       try (InputStream in = classPathResource.getInputStream()) {
           byte[] bytes = in.readAllBytes();
           int read = in.read(bytes);
           ByteBuffer buffer = ByteBuffer.wrap(bytes);
           badge = charset.decode(buffer).toString();
           badge = badge.replaceAll("\\{(name)}", name).replaceAll("\\{(handle)}", handle);
       }
       catch (Exception e) {
           System.out.println(e.toString()+" "+e.getMessage());
       }
       return badge;
    }
}
