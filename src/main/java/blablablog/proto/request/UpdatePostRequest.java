package blablablog.proto.request;

import java.util.ArrayList;
import java.util.List;

public class UpdatePostRequest implements IBlablablogRequest {
    private String id;
    private String title;
    private String body;
    private List<String> tags;

    public UpdatePostRequest() {
    }


    public UpdatePostRequest(String id, String title, String body, List<String> tags) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
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

    @Override
    public boolean isValid() {
        if (id == null || id.length() == 0 ||
                title == null || title.length() == 0 ||
                body == null || body.length() == 0 ||
                tags == null || tags.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Build test post request
     * @return post request
     */
    public static UpdatePostRequest buildRequest(String id) {
        UpdatePostRequest request = new UpdatePostRequest();
        request.setId(id);
        List<String> tags = new ArrayList<>();
        tags.add("BUSINESS");
        request.setTitle("First post");
        request.setBody("Lorem ipsum dolore");
        request.setTags(tags);
        return request;
    }

    @Override
    public String toString() {
        return "UpdatePostRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                '}';
    }
}
