package order.service;

import order.domain.*;

import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    CreateOrderResult create(Order newOrder);

    Order saveChanges(Order order);

    Order cancelOrder(CancelOrderInfo coi);

    ArrayList<Order> queryOrders(QueryInfo qi);

    Order alterOrder(OrderAlterInfo oai);

}
