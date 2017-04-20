package rebook.service;

import rebook.domain.Order;
import rebook.domain.OrderAlterInfo;

public interface OrderRebookService {

    Order alterOrder(OrderAlterInfo oai);

}
