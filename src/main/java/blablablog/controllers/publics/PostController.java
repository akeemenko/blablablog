package blablablog.controllers.publics;

import blablablog.BlablablogService;
import blablablog.entity.Post;
import blablablog.proto.PostProto;
import blablablog.proto.PostProtoList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("public/posts/")
public class PostController {

    /**
     * Get post for main page
     * @return list of posts
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PostProtoList getPosts() {
        PostProtoList protos = new PostProtoList();
        List<Post> entities = BlablablogService.getInstance().getAllPosts();
        for (Post post : entities) {
            protos.add(new PostProto(post));
        }
      return protos;
    }

}
