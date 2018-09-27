package blablablog.utils.springapp;

import java.io.InputStream;

/**
 * @author Mr. IP.
 */
public abstract class AbstractSpringContextSource {

    public abstract boolean isAvailable();

    public abstract InputStream getInputStream() throws SpringApplicationException;

}
