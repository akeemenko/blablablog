package blablablog.exceptions;

/**
 * Created by UNKNOWN on 08.11.17.
 */
public enum BlablablogSuccessCodes {
    SUCCESS_ALERT_USER_REGISTERED,
    SUCCESS_ALERT_WELCOME,
    SUCCESS_ALERT_ACTIVATED,
    SUCCESS_ALERT_SENT_ALERT_WITH_INSTRUCTIONS,
    SUCCESS_ALERT_NEW_PASSWORD_SENT;

    public String code() {
        return this.toString().toLowerCase();
    }
}
