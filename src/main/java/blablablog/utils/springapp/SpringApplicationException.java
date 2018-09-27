package blablablog.utils.springapp;

/**
 * @author Mr. IP.
 */
public class SpringApplicationException extends Exception {

    public SpringApplicationException(String message) {
        super(message);
    }

    public SpringApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

