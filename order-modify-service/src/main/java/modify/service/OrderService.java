package modify.service;

import modify.domain.Order;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    Order saveChanges(Order order);

}
