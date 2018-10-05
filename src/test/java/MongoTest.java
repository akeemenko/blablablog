import blablablog.BlablablogService;
import blablablog.entity.Post;
import blablablog.proto.request.CreatePostRequest;
import blablablog.proto.request.UpdatePostRequest;
import blablablog.utils.springapp.ApplicationSpringContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MongoTest {

    private static BlablablogService service;
    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext context = ApplicationSpringContext.getFromEmbedSource("/etc/blablablog/blablablog_test.xml");
        service = BlablablogService.init(context);
        service.clear();
    }

    @Test
    public void crudPost() throws Exception{
        // save
        List<Post> savedPosts = service.savePost(CreatePostRequest.buildRequest());
        assertEquals(1, savedPosts.size());

        // select
        List<Post> allPosts = service.getAllPosts();
        assertEquals(1, allPosts.size());

        // update
        UpdatePostRequest updatePostRequest = UpdatePostRequest.buildRequest(savedPosts.get(0).getId().toString());
        List<Post> updatedPosts = service.updatePost(updatePostRequest);
        assertEquals(1, updatedPosts.get(0).getTags().size());

        // delete
        service.deletePost(allPosts.get(0).getId().toString());
        assertEquals(0, service.getDatastore().find(Post.class).asList().size());
    }
}
