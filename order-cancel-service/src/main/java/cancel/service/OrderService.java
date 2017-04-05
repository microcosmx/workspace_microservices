package cancel.service;

import cancel.domain.CancelOrderInfo;
import cancel.domain.Order;
import java.util.ArrayList;

public interface OrderService {

    Order findOrderById(long id);

    Order cancelOrder(CancelOrderInfo coi);


}
