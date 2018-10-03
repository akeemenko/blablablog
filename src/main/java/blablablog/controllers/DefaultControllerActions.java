package blablablog.controllers;

import blablablog.exceptions.BlablablogException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultControllerActions {

    private static final Logger log = Logger.getLogger(DefaultControllerActions.class.getName());

    /**
     * Handle rest exception
     *
     * @param response action response
     * @param ex       rest exception
     * @throws IOException on response send error
     */
    @ExceptionHandler({BlablablogException.class})
    public void handleRestException(HttpServletResponse response, BlablablogException ex) throws IOException {
        log.error("Blablablog api exception" + " . " + ex.getMessage(), ex);
        String code = ex.getUserCode().toString().toLowerCase();
        response.setHeader("ERROR", code);
        response.sendError(ex.getUserCode().getServerAnswer(), code);
    }
}
