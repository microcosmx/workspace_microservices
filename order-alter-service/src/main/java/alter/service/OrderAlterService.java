package alter.service;

import alter.domain.Order;
import alter.domain.OrderAlterInfo;

public interface OrderAlterService {

    Order alterOrder(OrderAlterInfo oai);

}
