package blablablog.entity;

import blablablog.proto.request.CreatePostRequest;
import blablablog.utils.LazyDate;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Entity("posts")
public class Post {

    enum Tag {
        BUSSINES,
        TECHNOLOGY,
        FASHION,
        SPROTS,
        ECONOMY
    }

    @Id
    private ObjectId id;
    private String title;
    private String permaLink;
    private String body;
    private List<String> tags;
    private String author;
    private List<Comment> comments;
    private int createTimestamp;
    private int updateTimestamp;


    public Post() {
        this.createTimestamp = LazyDate.getUnixTimestamp();
        this.tags = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post(CreatePostRequest request) {
        this();
        this.title = request.getTitle();
        this.permaLink = toSlug(request.getTitle());
        this.body = request.getBody();
        this.tags = request.getTags();
        this.author = request.getLogin();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(int createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public int getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(int updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    /**
     * Make slug url from title
     * @param title post title
     * @return slug url
     */
    public static String toSlug(String title) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");

        String nowhitespace = WHITESPACE.matcher(title).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Build test post
     * @return post
     */
    public static Post buildPost() {
        return new Post(CreatePostRequest.buildRequest());
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", permaLink='" + permaLink + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", author='" + author + '\'' +
                ", comments=" + comments +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                '}';
    }
}