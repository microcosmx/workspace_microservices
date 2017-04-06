package cancel.service;

import cancel.domain.CancelOrderInfo;
import cancel.domain.Order;
import java.util.UUID;

public interface OrderService {

    Order findOrderById(UUID id);

    Order cancelOrder(CancelOrderInfo coi);

}
