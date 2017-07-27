package inside_payment.domain;

import java.util.UUID;

/**
 * Created by hh on 2017-07-27.
 */
public class ModifyOrderStatus {
    private String id;
    private String accountId;
    private int modify;   //1表示进入支付，2表示退出支付

    public ModifyOrderStatus(){
        UUID id = UUID.randomUUID();
        this.id = id.toString();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }
}
