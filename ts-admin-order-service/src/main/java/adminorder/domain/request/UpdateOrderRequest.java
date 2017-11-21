package adminorder.domain.request;

import adminorder.domain.bean.Order;

public class UpdateOrderRequest {
    private String id;
    private Order order;

    public UpdateOrderRequest(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
