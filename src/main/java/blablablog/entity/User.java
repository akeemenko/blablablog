package blablablog.entity;

import blablablog.utils.LazyDate;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("users")
public class User {
    @Id
    private ObjectId id;
    private String email;
    private String login;
    private String password;
    private String name;
    private int createTimestamp;


    public User() {
        this.createTimestamp = LazyDate.getUnixTimestamp();
    }

    public User(String email, String login, String password, String name) {
        this();
        this.email = email;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(int createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }
}
