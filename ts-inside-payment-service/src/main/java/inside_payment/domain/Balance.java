package inside_payment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/6/20.
 */
@Document(collection="balance")
public class Balance {
    @Valid
    @NotNull
    @Id
    private String userId;

    @Valid
    @NotNull
    private String balance;

    public Balance(){}

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
