package blablablog.utils.springapp;

import org.apache.log4j.Logger;

import java.io.InputStream;

/**
 * @author Mr. IP.
 */
public class EmbedFileContextSource extends AbstractSpringContextSource {

    private static final Logger log = Logger.getLogger(EmbedFileContextSource.class.getName());

    public final String path;
    public InputStream is;

    public EmbedFileContextSource(String path) {
        this.path = path;
    }

    @Override
    public boolean isAvailable() {
        try {
            return getInputStream() != null;
        } catch (SpringApplicationException e) {
            log.warn("Unable to check if resource is available. " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public InputStream getInputStream() throws SpringApplicationException {
        if (is == null) {
            is = ClassLoader.getSystemResourceAsStream(path);
        }
        return is;
    }

    @Override
    public String toString() {
        return "EmbedFileContextSource{" +
                "path='" + path + '\'' + '}';
    }
}
