package alter.domain;

public class OrderAlterInfo {

    private long accountId;
    private long previousOrderId;
    private Order newOrderInfo;

    public OrderAlterInfo(){
        accountId = 313173918;
        previousOrderId = 1;
        newOrderInfo = new Order();
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getPreviousOrderId() {
        return previousOrderId;
    }

    public void setPreviousOrderId(long previousOrderId) {
        this.previousOrderId = previousOrderId;
    }

    public Order getNewOrderInfo() {
        return newOrderInfo;
    }

    public void setNewOrderInfo(Order newOrderInfo) {
        this.newOrderInfo = newOrderInfo;
    }
}
