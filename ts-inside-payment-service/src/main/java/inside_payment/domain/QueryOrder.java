package inside_payment.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class QueryOrder {

    private String orderNumber;

    public QueryOrder(){}

    public QueryOrder(String orderNumber){
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
