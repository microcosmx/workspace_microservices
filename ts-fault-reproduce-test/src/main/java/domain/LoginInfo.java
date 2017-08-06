package domain;

public class LoginInfo {

    private String email;

    private String password;

    private String verificationCode;

    public LoginInfo(String email,String password,String code) {
        this.email = email;
        this.password = password;
        this.verificationCode = code;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
