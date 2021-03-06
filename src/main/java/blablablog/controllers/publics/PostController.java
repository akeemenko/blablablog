package blablablog.controllers.publics;

import blablablog.BlablablogService;
import blablablog.entity.Post;
import blablablog.exceptions.BlablablogException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("public/posts/")
public class PostController {

    /**
     * Get hot post
     * @param limit limit
     * @return list of posts
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public List<Post> hotPosts(@RequestParam("limit") int limit) {
        return BlablablogService.getInstance().getHotPosts(limit);
    }

    /**
     * Get last post
     * @param limit limit
     * @return list of posts
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public List<Post> lastPosts(@RequestParam("limit") int limit) {
        return BlablablogService.getInstance().getLastPosts(limit);
    }

    /**
     * Get post by permalink
     * @param permalink post permalink
     * @return post entity
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Post getPost(@RequestParam("permalink") String permalink) throws BlablablogException {
        return BlablablogService.getInstance().getPostByPermalink(permalink);
    }

}
