import blablablog.BlablablogService;
import blablablog.entity.Post;
import blablablog.proto.CreatePostRequest;
import blablablog.proto.UpdatePostRequest;
import blablablog.utils.springapp.ApplicationSpringContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.query.Query;
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
        List<Post> foundPosts = service.getDatastore().find(Post.class)
                .field("permaLink").equal(savedPosts.get(0).getPermaLink())
                .asList();
        assertEquals(1, foundPosts.size());

        // update
        UpdatePostRequest updatePostRequest = UpdatePostRequest.buildRequest(savedPosts.get(0).getId().toString());
        List<Post> posts = service.updatePost(updatePostRequest);
        assertEquals(1, posts.get(0).getTags().size());

        // delete
        final Query<Post> postForDelete = service.getDatastore()
                .find(Post.class)
                .field("permaLink").equal(savedPosts.get(0).getPermaLink());
        service.getDatastore().delete(postForDelete);
        assertEquals(0, service.getDatastore().find(Post.class).asList().size());
    }
}
