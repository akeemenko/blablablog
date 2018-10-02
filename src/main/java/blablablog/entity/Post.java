package blablablog.entity;

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

    public Post(String title, String body, List<String> tags, String author, List<Comment> comments) {
        this.title = title;
        this.permaLink = toSlug(title);
        this.permaLink =
        this.body = body;
        this.tags = tags;
        this.author = author;
        this.comments = comments;
    }

    /**
     * Make slug url from title
     * @param title post title
     * @return slug url
     */
    private String toSlug(String title) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");

        String nowhitespace = WHITESPACE.matcher(title).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}