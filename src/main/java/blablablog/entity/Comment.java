package blablablog.entity;

import blablablog.utils.LazyDate;

public class Comment {
    private String name;
    private String email;
    private String message;
    private int createTimestamp;

    public Comment() {
        this.createTimestamp = LazyDate.getUnixTimestamp();
    }

    public Comment(String name, String email, String message) {
        this();
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(int createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }
}
