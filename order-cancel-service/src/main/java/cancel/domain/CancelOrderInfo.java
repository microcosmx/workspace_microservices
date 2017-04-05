package cancel.domain;

public class CancelOrderInfo {

    private long accountId;
    private long orderId;

    public CancelOrderInfo(){
        accountId = 313173918;
        orderId = 1;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
