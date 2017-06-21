package inside_payment.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class PaymentInfo {
    public PaymentInfo(){}

    private String orderNumber;
    private String tripId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
