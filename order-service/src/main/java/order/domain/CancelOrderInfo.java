package order.domain;

import java.util.UUID;

public class CancelOrderInfo {

    private UUID accountId;

    private UUID orderId;

    public CancelOrderInfo(){

    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
