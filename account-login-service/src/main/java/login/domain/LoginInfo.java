package login.domain;

/**
 * LoginInfo: Container for saving the login information.
 */

public class LoginInfo {

    private String phoneNum;

    private String password;

    public LoginInfo() {
        this.phoneNum = "";
        this.password = "";
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
