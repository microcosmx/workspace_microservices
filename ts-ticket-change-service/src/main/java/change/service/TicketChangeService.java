package change.service;

import change.domain.OrderCancelInfo;
import change.domain.OrderCancelResult;
import change.domain.OrderChangeInfo;
import change.domain.OrderChangeResult;

public interface TicketChangeService {

    OrderCancelResult cancelOrder(OrderCancelInfo orderCancelInfo);

    OrderChangeResult changeOrder(OrderChangeInfo orderChangeInfo);

}
