package domain;

/**
 * LoginInfo: Container for get the login information.
 */

public class LoginInfo {

    private long id;

    private String password;

    public LoginInfo() {
        this.id = 0;
        this.password = "";
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
