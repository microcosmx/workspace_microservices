package orders.service;

import orders.domain.Order;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    ArrayList<Order> findOrdersByAccountId(UUID accountId);

    Order create(Order newOrder);

    Order saveChanges(Order order);

}
