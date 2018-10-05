package blablablog.proto.request;

public class CreateUserRequest implements IBlablablogRequest {
    private String email;
    private String login;
    private String password;
    private String name;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String email, String login, String password, String name) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.name = name;
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

    @Override
    public boolean isValid() {
        if (email == null || email.length() < 3 ||
                login == null || login.length() < 3 ||
                password == null || password.length() != 64 ||
                name == null || name.length() < 3) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
