package blablablog.controllers.privates;

import blablablog.BlablablogService;
import blablablog.entity.Post;
import blablablog.exceptions.BlablablogErrorCodes;
import blablablog.exceptions.BlablablogException;
import blablablog.proto.request.CreatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("private")
public class PrivateController {

    /**
     * Save image
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "createPost", method = RequestMethod.POST)
    public void saveAvatar(@RequestBody CreatePostRequest request, HttpServletRequest req) throws BlablablogException {
        CreatePostRequest r = request;
    }

}
