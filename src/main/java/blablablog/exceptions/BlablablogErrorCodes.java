package blablablog.exceptions;

/**
 * Created by UNKNOWN on 08.11.17.
 */
public enum BlablablogErrorCodes {
    ERROR_SERVER_ERROR(500),
    ERROR_USER_ALREADY_REGISTER(400),
    ERROR_ACCOUNT_NOT_ACTIVATED(400),
    ERROR_FORBIDDEN(403),
    ERROR_FORBIDDEN_SPACE(400),
    ERROR_USER_NOT_FOUND(400),
    ERROR_USER_WITH_REFERRAL_NOT_FOUND(400),
    ERROR_PRECONDITION_FAILED(412),
    ERROR_ACCESS_DENIED(403),
    ERROR_WRONG_EMAIL(403);

    private final int serverAnswer;

    BlablablogErrorCodes(int serverAnswer) {
        this.serverAnswer = serverAnswer;
    }

    BlablablogErrorCodes() {
        this.serverAnswer = 400;
    }

    public int getServerAnswer() {
        return serverAnswer;
    }

}
