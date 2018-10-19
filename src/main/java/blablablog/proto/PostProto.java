package blablablog.proto;

import blablablog.entity.Comment;
import blablablog.entity.Post;

import java.util.List;

public class PostProto {
    private String id;
    private String title;
    private String permaLink;
    private String description;
    private String body;
    private List<String> tags;
    private String author;
    private List<Comment> comments;
    private int createTimestamp;
    private int updateTimestamp;

    public PostProto() {
    }

    public PostProto(Post post) {
        this.id = post.getId().toString();
        this.title = post.getTitle();
        this.permaLink = post.getPermalink();
        this.description = post.getDescription();
        this.body = post.getBody();
        this.tags = post.getTags();
        this.author = post.getAuthor();
        this.comments = post.getComments();
        this.createTimestamp = post.getCreateTimestamp();
        this.updateTimestamp = post.getUpdateTimestamp();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "PostProto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", permaLink='" + permaLink + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", author='" + author + '\'' +
                ", comments=" + comments +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                '}';
    }
}
