package blablablog.proto.request;

import java.util.ArrayList;
import java.util.List;

public class CreatePostRequest implements IBlablablogRequest {
    private String title;
    private String description;
    private String body;
    private List<String> tags;
    private String login;

    public CreatePostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean isValid() {
        if (title == null || title.length() == 0 ||
                description == null || description.length() == 0 ||
                body == null || body.length() == 0 ||
                tags == null || tags.size() == 0 ||
                login == null || login.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Build test post request
     * @return post request
     */
    public static CreatePostRequest buildRequest() {
        CreatePostRequest request = new CreatePostRequest();
        List<String> tags = new ArrayList<>();
        tags.add("BUSINESS");
        tags.add("LOCAL");
        request.setTitle("This is title of post");
        request.setDescription("This is small description about post");
        request.setBody("This is body of post");
        request.setTags(tags);
        request.setLogin("Admin");
        return request;
    }

    @Override
    public String toString() {
        return "CreatePostRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", login='" + login + '\'' +
                '}';
    }
}
