package order.domain;

public class ModifyOrderInfo {

    private String orderId;

    private int status;

    public ModifyOrderInfo() {
        //Default Constructor
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
