package uhs.alphabet.domain.badge;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StuBadge {
    private final String name;
    private final String handle;
    private final String badge;
    private final ResourceLoader loader;

    public StuBadge(final String name, final String handle, ResourceLoader loader) {
        this.name = name;
        this.handle = handle;
        this.loader = loader;
        String ret = "";
        try {
            Resource resource = loader.getResource("classpath:/static/badge/stuBadge");
            InputStream in = resource.getInputStream();
            byte[] bytes = in.readAllBytes();
            int read = in.read(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            Charset charset = Charset.forName("UTF-8");
            ret = charset.decode(buffer).toString();
            ret = ret.replaceAll("\\{(name)}", name).replaceAll("\\{(handle)}", handle);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        badge = ret;
    }

    public String getBadge() {
        return badge;
    }
}
