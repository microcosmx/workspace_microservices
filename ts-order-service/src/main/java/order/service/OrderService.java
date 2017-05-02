package order.service;

import order.domain.CancelOrderInfo;
import order.domain.Order;
import order.domain.OrderAlterInfo;
import order.domain.QueryInfo;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    Order create(Order newOrder);

    Order saveChanges(Order order);

    Order cancelOrder(CancelOrderInfo coi);

    ArrayList<Order> queryOrders(QueryInfo qi);

    Order alterOrder(OrderAlterInfo oai);


}
