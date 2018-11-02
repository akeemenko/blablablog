package blablablog.controllers.privates;

import blablablog.BlablablogService;
import blablablog.exceptions.BlablablogErrorCodes;
import blablablog.exceptions.BlablablogException;
import blablablog.proto.request.CreatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableWebMvc
@RequestMapping("private")
public class PrivateController {

    /**
     * Save image
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "create-post", method = RequestMethod.POST)
    public void saveAvatar(@RequestBody CreatePostRequest request, HttpServletRequest req) throws BlablablogException {
        CreatePostRequest r = request;
        if (!request.isValid()) {
            throw new BlablablogException(BlablablogErrorCodes.ERROR_PRECONDITION_FAILED, "Invalid create post request");
        }
        BlablablogService.getInstance().savePost(request);
    }

}
