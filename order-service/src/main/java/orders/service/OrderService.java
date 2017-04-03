package orders.service;

import orders.domain.Order;
import java.util.ArrayList;

public interface OrderService {

    Order findOrderById(long id);

    ArrayList<Order> findOrdersByAccountId(long accountId);

    Order create(Order newOrder);

    Order saveChanges(Order order);

}
