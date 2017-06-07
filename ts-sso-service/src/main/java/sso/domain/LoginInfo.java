package sso.domain;

public class LoginInfo {

    private String phoneNum;

    private String password;

    private String verificationCode;

    public LoginInfo() {
        this.phoneNum = "";
        this.password = "";
        this.verificationCode = "";
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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
