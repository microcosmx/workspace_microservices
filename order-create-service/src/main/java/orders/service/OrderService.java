package orders.service;

import orders.domain.Order;
import java.util.ArrayList;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    Order create(Order newOrder);


}
