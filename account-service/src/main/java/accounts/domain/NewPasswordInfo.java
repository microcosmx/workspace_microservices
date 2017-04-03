package accounts.domain;

/**
 * LoginInfo: Container for get the password-change information.
 */
public class NewPasswordInfo {

    private long id;
    private String oldPassword;
    private String newPassword;

    public NewPasswordInfo(){
        id = -1;
        oldPassword = "";
        newPassword = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
