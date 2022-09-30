package uhs.alphabet.badge.application;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import uhs.alphabet.badge.adapter.RankedBadgeFile;
import uhs.alphabet.badge.domain.Website;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Component
public class CodeforcesBadgeFile implements RankedBadgeFile {
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
            throw new RuntimeException(e.getMessage());
        }
        badge = charset.decode(buffer).toString();
    }
    @Override
    public String getBadge() {
        return badge;
    }

    @Override
    public Website getFrom() {
        return Website.CODEFORCES;
    }
}
