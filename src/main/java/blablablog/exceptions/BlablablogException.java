package blablablog.exceptions;

/**
 * Created by UNKNOWN on 22.01.2018.
 */
public class BlablablogException extends Exception {
    private BlablablogErrorCodes userCode;

    public BlablablogException() {
        super();
        this.userCode = BlablablogErrorCodes.ERROR_SERVER_ERROR;
    }

    public BlablablogException(String message) {
        super(message);
        this.userCode = BlablablogErrorCodes.ERROR_SERVER_ERROR;
    }

    public BlablablogException(BlablablogErrorCodes code, String message) {
        super(message);
        this.userCode = code;
    }

    public BlablablogException(String message, Throwable cause) {
        super(message, cause);
        this.userCode = BlablablogErrorCodes.ERROR_SERVER_ERROR;
    }

    public BlablablogException(BlablablogErrorCodes code, String message, Throwable cause) {
        super(message, cause);
        this.userCode = code;
    }

    public BlablablogErrorCodes getUserCode() {
        return userCode;
    }

    public String code() {
        return userCode.toString().toLowerCase();
    }
}