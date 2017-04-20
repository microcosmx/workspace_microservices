package rebook.domain;

import java.util.UUID;

public class OrderAlterInfo {

    private UUID accountId;
    private UUID previousOrderId;
    private Order newOrderInfo;

    public OrderAlterInfo(){
        newOrderInfo = new Order();
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getPreviousOrderId() {
        return previousOrderId;
    }

    public void setPreviousOrderId(UUID previousOrderId) {
        this.previousOrderId = previousOrderId;
    }

    public Order getNewOrderInfo() {
        return newOrderInfo;
    }

    public void setNewOrderInfo(Order newOrderInfo) {
        this.newOrderInfo = newOrderInfo;
    }
}
