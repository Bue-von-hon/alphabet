package uhs.alphabet.domain.badge;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class StuBadge {
    private final String name;
    private final String handle;
    private final String badge;

    public StuBadge(final String name, final String handle) {
        this.name = name;
        this.handle = handle;
        String ret = "";
        try {
            Path stuBadge = Path.of(ClassLoader.getSystemResource("static/badge/stuBadge").toURI());
            byte[] bytes = Files.readAllBytes(stuBadge);
            Charset charset = Charset.forName("UTF-8");
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            ret = charset.decode(buffer).toString()
                    .replaceAll("\\{(name)}", name)
                    .replaceAll("\\{(handle)}", handle);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        badge = ret;
    }

    public String getBadge() {
        return badge;
    }
}
