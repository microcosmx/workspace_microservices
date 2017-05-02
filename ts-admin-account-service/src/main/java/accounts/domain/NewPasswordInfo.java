package accounts.domain;

import java.util.UUID;

public class NewPasswordInfo {

    private UUID id;
    private String oldPassword;
    private String newPassword;

    public NewPasswordInfo(){
        oldPassword = "";
        newPassword = "";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
