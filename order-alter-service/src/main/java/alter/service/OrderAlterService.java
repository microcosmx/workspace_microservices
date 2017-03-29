package alter.service;

import alter.domain.Order;

import java.util.ArrayList;

public interface OrderAlterService {

    Order findOrderById(long id);

    ArrayList<Order> findOrdersByAccountId(long accountId);

    Order create(Order newOrder);

    Order saveChanges(Order order);

}
