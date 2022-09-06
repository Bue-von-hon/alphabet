package uhs.alphabet.domain.badge;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

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
            Resource resource = loader.getResource("classpath:static/badge/stuBadge");
            File file = resource.getFile();
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                ret+=tmp;
            }
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
