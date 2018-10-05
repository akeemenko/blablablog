package blablablog;

import blablablog.entity.Post;
import blablablog.exceptions.BlablablogException;
import blablablog.mongo.MongoConfig;
import blablablog.mongo.MongoConnection;
import blablablog.proto.request.CreatePostRequest;
import blablablog.proto.request.UpdatePostRequest;
import blablablog.utils.LazyDate;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static blablablog.exceptions.BlablablogErrorCodes.ERROR_SERVER_ERROR;

public class BlablablogService {
    private static final Logger log = Logger.getLogger(BlablablogService.class.getName());
    private static BlablablogService instance;
    private final Datastore datastore;


    public BlablablogService(ApplicationContext applicationContext) {
        MongoConnection conn = MongoConnection.getInstance();
        conn.init((MongoConfig) applicationContext.getBean("mongoConfiguration"));
        this.datastore = conn.getDataStore();
        instance = this;
    }

    /**
     * Init blablablog service
     *
     * @param applicationContext application context
     * @return instance of blablablog service
     */
    public static BlablablogService init(ApplicationContext applicationContext) {
        if (instance != null) {
            log.fatal("Unable to initialize new instance of Blablablog service");
            return instance;
        } else {
            return new BlablablogService(applicationContext);
        }
    }

    /**
     * Drop DB
     */
    public void clear() {
        datastore.getDB().dropDatabase();
    }




    /**
     * Get all posts
     * @return list of posts
     */
    public List<Post> getAllPosts() {
        return datastore.find(Post.class).asList();
    }

    /**
     * Get post by id
     * @param id post id
     * @return post entity
     */
    private Post getPostByID(String id) throws BlablablogException {
        ObjectId objectId = new ObjectId(id);
        Post post = datastore.get(Post.class, objectId);
        if (post == null) {
            throw new BlablablogException(ERROR_SERVER_ERROR, "Unable to find post with id = " + id);
        }
        return post;
    }

    /**
     * Save post
     * @param request post request
     * @return list of posts
     */
    public List<Post> savePost(CreatePostRequest request) {
        final Post post = new Post(request);
        datastore.save(post);
        return getAllPosts();
    }

    /**
     * Update post
     * @param request update post request
     * @return list of posts
     */
    public List<Post> updatePost(UpdatePostRequest request) throws BlablablogException {
        // find post by id
        Post post = getPostByID(request.getId());
        // update post
        post.setBody(request.getBody());
        post.setTitle(request.getTitle());
        post.setPermaLink(Post.toSlug(request.getTitle()));
        post.setTags(request.getTags());
        post.setUpdateTimestamp(LazyDate.getUnixTimestamp());
        datastore.merge(post);
        return getAllPosts();
    }

    /**
     * Delete post by id
     * @param id post id
     */
    public void deletePost(String id) throws BlablablogException {
        Post post = getPostByID(id);
        datastore.delete(post);
    }




    public static BlablablogService getInstance() {
        return instance;
    }

    public Datastore getDatastore() {
        return datastore;
    }
}