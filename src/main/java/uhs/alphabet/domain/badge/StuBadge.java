package uhs.alphabet.domain.badge;

import org.springframework.core.io.ClassPathResource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class StuBadge {
    private static final Charset charset = Charset.forName("UTF-8");
    private static final String data;
    static {
        ClassPathResource classPathResource = new ClassPathResource("/static/badge/stuBadge");
        String ret = "";
        try {
            File file = classPathResource.getFile();
            FileReader fileReader = new FileReader(file);
            CharBuffer charBuffer = CharBuffer.allocate((int) file.length());
            int read = fileReader.read(charBuffer);
            charBuffer.flip();
            ret = charBuffer.toString();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = ret;
    }
    public static String of2(final String name, final String handle) {
        return data
                .replaceAll("\\{(name)}", name)
                .replaceAll("\\{(handle)}", handle);
    }
    public static String of1(final String name, final String handle) {
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
           System.out.println(e.getMessage());
       }
       return badge;
    }
}
