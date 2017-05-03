package order.service;

import order.domain.*;

import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    CreateOrderResult create(Order newOrder);

    ChangeOrderResult saveChanges(Order order);

    CancelOrderResult cancelOrder(CancelOrderInfo coi);

    ArrayList<Order> queryOrders(QueryInfo qi);

    Order alterOrder(OrderAlterInfo oai);

}
