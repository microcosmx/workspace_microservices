package register.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class CreateAccountInfo {

    private String userId;
    private String balance;

    public CreateAccountInfo(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
