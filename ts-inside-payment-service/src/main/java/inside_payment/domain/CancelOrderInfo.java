package inside_payment.domain;

public class CancelOrderInfo {

    private String orderId;

    public CancelOrderInfo() {
        //Default Constructor
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
